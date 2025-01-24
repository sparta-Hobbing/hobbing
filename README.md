## 서비스 기획

Hobbing은 다양한 원데이 클래스를 검색, 예약, 결제할 수 있는 통합 플랫폼입니다.

사용자는 클래스 목록을 탐색하고 일정에 맞는 클래스를 쉽게 예약할 수 있으며, 강사는 클래스 등록과 예약 관리가 가능한 환경을 제공합니다. 

MSA 구조를 기반으로 설계되어 유저 관리, 강의 관리, 예약, 결제, 쿠폰 등 각 기능의 독립적인 확장과 유지보수가 용이합니다. 

## 프로젝트 목표

원데이 클래스 예약 서비스 (참고 서비스 예시 : 탈잉)

- 대규모 트래픽 처리(**Jmeter, Kafka)**
- Docker를 활용한 독립적 운영 환경 구성
- Redis를 활용한 캐싱 전략(실시간 예매, 순위)
- GitPackages를 사용
- 모니터링(Prometheus, Grafana)

## 인프라 설계도

`최종(최신화된) 인프라 설계도를 넣어주세요.(MSA 시스템 흐름, CI/CD포함)`

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/83c75a39-3aba-4ba4-a792-7aefe4b07895/ba94b5d5-a797-44b6-9a16-0c3ae5df32cb/image.png)

## 서비스 구조도

## 주요 기능

**강의**

- 강의 관련 CRUD : 강의 등록, 수정, 삭제, 조회 기능 제공
- 스케줄 관리 (LectureSchedule) 강의 일정 생성, 수정, 삭제, 조회 기능.
- 학생 등록 및 출석 상태 완료 상태 관리
- 강사(튜터)  정보 및 강의 등록 기능.

**예약 및 결제/정산**

- 예약, 강의 결제/정산 관련 api
- 결제 기한 지난 예약 삭제

**강의 예약 대기열**

- redis로 대기열 구현

**쿠폰**

- 쿠폰 생성, 발급, 수정, 삭제, 사용 및 조회
- 쿠폰 기한 만료시 자동 비활성화 처리
- 결제 취소 시 쿠폰 복원

## 기술적 의사결정

- **Redis를 사용한 예매 대기열**
    - 도입 배경
        
        인기 강의의 예매 시작등으로 인한 원인으로 트래픽 부하가 몰릴때, 예매 트래픽으로 인한 부하를 분산할 필요성
        
    - 기술 선택 고려사항
        - 특정 대기열 서버 인스턴스가 다운시에도 이를 복구할 수 있도록 고려할 수 있어야함
        - rdb에 동기적으로 저장하는것 보단 더 빠른 속도로 처리가능해야함
    - 기술 비교군
        - kafka
            - 장점
                - 영속 저장 통해 대기열 장애시 복구 가능
                
                - 비동기 처리 통한 응답성능 향상
            - 단점
                - fifo queue 구현 위해선 한 파티션에 한 강의만 저장해야함, 파티션 너무 많아질 가능성
        - rabbitMQ
            - 장점
                - 영속 저장 통해 대기열 장애시 복구 가능
                - 비동기 처리 통한 응답성능 향상 가능
            - 단점
                - 대기열 트래픽 늘어날 시, replica방식의 클러스터링만 기본 지원. 불필요한 데이터 중복 저장 발생
        - redis
            - 장점
                - inmemory방식으로 빠른 응답성능
                - 기본적으로 싱글 스레드 + atomic operation으로 인한 race condition 자동 방지
                - 완전하진 않지만 rdb+aof 기능을 통한 데이터 백업 가능
                - 클러스터링 시 샤딩방식을 기본 지원, 트래픽 부하 늘어나도 효율적 대응 가능
            - 단점
                - rdb + aof로 백업 시, 백업 주기에 따라 약간의 데이터 손실 가능성
        - 결론
            - 트래픽에 효율적으로 대응가능하고, 백업 데이터 저장도 가능한 redis를 선택, 
            aof 주기 조절을 통해 데이터 손실 가능성 최소화 하는 방향으로 구현
    
    ### Redis 대기열 특성
    
    **동시성 문제**
    Redis는 기본적으로 싱글 스레드와 atomic연산을 통해 race condition을 방지 가능 합니다.
    sorted set의 기능을 통해 대기열과 같은 기능구현을 간편하게 구현가능하면서도 동시성 문제를 예방할 수 있습니다.
    
    **백업 기능**
    또한 aof/rdb기능을 활용한 백업을 통해, 서비스 장애 발생시 영속 저장된 fifo queue의 정보를 불러와 서비스를 복원하는것도 가능합니다.
    
    **데이터 규모에 따른 확장성**
    샤딩방식으로 대기열 데이터 처리량이 늘어나도 확장성과 성능을 모두 챙길 수 있는 점도 대기열 구현기술로 적합한 점이라 판단했습니다.
    
- **타 서버 장애 대비 db 모델링**
    
    msa구조에서 특정 서버 장애시에도 나머지는 정상작동 가능하단 장점을 살리기 위해,
    자주 사용되는 타 서비스 서버의 데이터는 중복저장하는 방식으로 테이블을 모델링 했습니다.
    
    너무 많이 중복 저장하면 테이블 규모 비대해지기에 적절한 타협점 필요했습니다. 이를 위한 기준을 몇가지 생각했었습니다.
    
    1. 되도록이면 변경 없거나 오랜기간 똑같이 유지되는 데이터 위주로 저장
    → 변경 잦은 데이터를 저장할시, 메시징으로 시스템 복잡도까진 해결해도, 불필요한 서버 부하 발생가능성
    2. 목록 조회 기능 위한 간단한 데이터만 중복 저장
    - msa구조상의 역할분리상, 목록 조회에 필요한 간단한타 서버 db의 데이터 관리는 내 서비스 서버의 담당이라 판단
    - msa구조 역할분리상, 목록 조회에서 필요한 타 서버의 데이터 관리는 내 서버의 담당이라 판단
    - 상세 조회에 필요한 데이터 호출의 책임은 타 서비스 담당이라 판단
        - 이런 부분까지 저장하면 테이블 너무 비대해짐
    
- **예약과 결제 절차 분리**
    - 결제까지 끝내는걸 기준으로 선착순 예약을 받으면 사용자 경험 악화 가능성
        - 예약 과정에서 pg사 결제 api연동상의 오류등으로 인한 선착순과 무관한 불이익 발생가능성
    - 예약 후 일정 기간 내 결제하게끔 예약과 결제 분리
        - 기간 내 결제하지 않을 시 자동 예약 취소
- **강의 결제 기한 조절해 공석 재신청 트래픽 부하 낮추기**
    
    **강의 결제 기한을 적절히 길게 설정해 공석 발생을 노리는 재신청 트래픽을 낮춤.**
    
    - **짧은 결제 기한의 문제점:**
        - 짧은 결제 기한은 미결제/결제 포기로 인한 공석을 노리는 사용자들의 지속적인 트래픽을 유발합니다.
        - 초기 신청 이후에도 이러한 현상이 반복되어 시스템에 부담을 줄 수 있습니다.
    - **긴 결제 기한의 장점:**
        - 예약자가 취소해 공석이 생기기까지의 기간이 길어지고, 공석 발생이 더 드문드문 일어나게됨
        - **미결제 감소:** 미결제로 인한 공석을 기다리는 시간이 길어져, 비슷한 다른 클래스를 찾는 걸 유도해 트래픽을 줄일 수 있음.
    - **사용자 경험:** 긴 결제 기한은 사용자 경험에 큰 영향을 미치지 않을 것으로 예상됩니다.
    - **주의사항:** 결제 기한이 너무 길어지면 강사에게 불리하게 작용할 수 있으므로 적절한 기간을 설정해야 합니다.
    - 결론:
        - 강사의 의견을 수렴하여 적절한 기간을 설정해야 합니다.
        - 실 사용자 데이터가 없으므로, 다른 서비스의 사례를 참고해 임의 기준 설정
- 강의와 스케쥴 관리
    
    강의와 스케줄 데이터를 각각 관리하던 방식이 데이터 무결성을 저해하고, 강의-스케줄-학생 간 관계가 명확하지 않아 참조 무결성 오류가 발생했습니다. 이를 해결하기 위해 강의와 스케줄을 통합 관리하고 데이터 간 연계성을 강화한 설계가 필요했습니다.
    
    ### **구현**
    
    1. **강의 CRUD**:
        - 강의 생성, 조회, 수정, 삭제 API 구현.
        - QueryDSL을 활용하여 조건부 검색 기능 제공.
        - 강의 데이터에 예약 시작 시간 필드 추가로 예약과 강의 간 유효성 검사 강화.
    2. **스케줄 관리**:
        - 강의와 연계된 스케줄 CRUD 구현.
        - 스케줄 변경 시 강의 정보와 자동 동기화 로직 추가.
        - 강의와 스케줄 간의 참조 무결성을 유지하도록 설계.
    3. **학생 출석 관리**:
        - 강의 참여 학생 등록 API 구현.
        - 출석 및 강의 완료 상태를 관리하는 로직 추가.
        - 강의 진행 상태와 연계된 출석 데이터의 동기화.
    
    ### **결과**
    
    - 강의, 스케줄, 학생 데이터를 하나의 연계된 구조로 통합하여 데이터 무결성과 관리 효율성을 강화.
    - 강의와 스케줄의 통합 설계로 강의 변경 사항이 스케줄과 동기화되며, 강의 운영의 편의성이 향상됨.
    - 강의 예약 시작 시간을 명확히 정의하여 예약 유효성 검사를 통해 사용자 경험을 개선.
- **쿠폰 만료**
    - 의사결정 배경
    
    쿠폰은 `expirationDate`를 기준으로 자동으로 만료되어야 하지만, 이를 관리자가 수동으로 처리하는 것은 비효율적이고 누락 가능성이 있습니다. 대량의 쿠폰 데이터를 관리하기 위해 자동화된 시스템이 필요했습니다. 또한, 쿠폰이 만료된 경우 해당 상태를 시스템 내에서 일관성 있게 반영하는 것이 중요했습니다.
    
    - 고려한 부분
        - 자동화된 만료 관리 : Spring의 스케줄러를 사용해 주기적으로 만료된 쿠폰을 비활성화.
        - 데이터베이스 성능 : 대량의 쿠폰 데이터를 한 번에 처리할 경우 데이터베이스 성능에 영향을 줄 수 있으므로, 페이징 처리 또는 배치 작업을 추가로 고려
    
    - 최종 구현
    
    Spring Scheduler의 `@Scheduled` 전에 만료된 쿠폰을 조회하고 상태를 `EXPIRED`로 업데이트하는 방식을 선택했습니다. 이는 관리자의 작업 부담을 줄이고, 데이터 일관성을 유지하며, 시스템 성능에 부담을 최소화하는 효율적인 방법이었습니다.
    
- **쿠폰 복원**
    - 의사결정 배경
    
    쿠폰이 사용된 이후, 결제가 취소되는 경우 사용자에게 쿠폰을 다시 복원해주는 로직이 필요했습니다. 이 과정에서 복원 가능 상태(`USED`)의 쿠폰만 처리해야 하며, 잘못된 접근(다른 사용자가 쿠폰 복원을 시도하는 등)을 방지하기 위한 인증 로직도 함께 구현해야 했습니다. 또한, 복원 시 이력을 기록하여 추적 가능성을 높이는 것도 고려해야 했습니다.
    
    - 고려한 부분
        - 사용자 인증: `@AuthenticationPrincipal`을 활용하여 현재 로그인된 사용자만 자신의 쿠폰을 복원할 수 있도록 구현
        - 복원 조건: 쿠폰 상태가 반드시 `USED`이어야 하며, 다른 상태(`ACTIVE`, `EXPIRED`)의 쿠폰은 복원되지 않도록 검증
        - 이력 관리: 복원 시간(`restoredAt`)을 기록해 복원 작업이 언제 이루어졌는지 추적 가능하도록 설계
        - 예외 처리: 잘못된 사용자 요청, 인증 실패, 또는 상태 조건을 충족하지 못한 경우 명확한 에러 메시지를 반환하도록 구현
        
    - 최종 구현
    
    최종적으로, `restoreCouponAfterPaymentCancellation` 메서드를 통해 사용자 인증을 거친 후, 복원 조건을 검증하여 쿠폰 상태를 `ACTIVE`로 업데이트하고, 복원 시간을 기록하는 방식을 선택했습니다. 이 설계는 인증과 검증 과정을 강화하면서도 복원 작업의 안정성을 보장합니다.
    
- 2계층 게이트웨이 + 인증, 인가
    - 도입 배경
    
     - 2계층 게이트웨이와 단일 게이트웨이에서의 비교
    
    | 구분 | 2계층 게이트웨이 | 단일 게이트웨이 |
    | --- | --- | --- |
    | 구조 | 외부/내부 트래픽 관리 분리 | 외부/내부 트래픽 통합 처리 |
    | 보안성 | 높은 보안 가능 | 상대적으로 낮음 |
    | 관리 복잡성 | 높음 | 낮음 |
    | 비용 | 높음 | 낮음 |
    | 장애 복구 | 계층별 복구 가능 | 단일 장애점으로 인해 복구 어려움 |
    | 확장성 | 네트워크 확장이 용이 | 네트워크 확장에 한계 |
    
    MSA 설계에서 서비스를 분리하고 분산환경이라고 가정을 했을 경우 단일 게이트웨이로 외부와 내부의 통신을 구분할 수 있는 방법이 무엇일까? 고민을 해보았습니다. 
    
     이전에 다녔던 보안 회사에서 알게 되었던 짧은 네트워크 지식 중 방화벽의 종류에서 착안해 2계층 게이트웨이를 도입하게 되었습니다.
    
    단일 게이트웨이의 경우 외부의 접근과 내부 서비스 간의 접근을 구분할 수 있는 방법이 쉽게 떠오르지 않았습니다. 외부와 내부를 확인하기 위한 플래그와 같은 방식의 변형 외에 떠오르지 않았습니다. 그에 비해 2계층 게이트웨이를 사용할 경우 검증 방식을 다르게 하는 적용하는 등의 방식이 쉽게 떠올랐습니다.
    
    모니터링 측면에서도 내부 게이트웨이를 중앙집중식으로 서비스를 관리할 수 있다고 생각했습니다. 외부접속을 외부게이트 서비스로 통일시켜서 내부 게이트웨이가 통제할 수 있는 방식으로 진행했을 경우 모니터링에서의 이점도 생긴다고 생각했습니다.
    
     외부 게이트웨이에서 인증을 진행하고 내부 게이트웨이에서 인가를 관리하여 개별 서비스에 들어가기 전에 가능여부를 판별하여 최대한 내부 서비스의 접근을 제한하고 네트워크를 덜 거칠 수 있도록 진행하도록 구성했습니다.
    
     외부 게이트웨이에서는 인증이라는 기능만을 가지고 소규모로 진행하고 내부 게이트웨이는 다수의 서비스 클러스터링을 구성하여 확장하여 사용할 수 있도록 진행할 수 있다고 생각했습니다.
    
    어려운 점은 네트워크의 구성과 신뢰도와 관리 복잡성, 비용측면에서 어떻게 처리할지 고민이 되었고 이 부분이 구현하는데 많은 어려움이 있었습니다.
    
    - 구현 후 느낀 점과 개선점
    
     2계층 게이트웨이로 외부와 내부를 구분할 수 있다는 이점은 확실히 메리트가 있다고 생각합니다. 모니터링과 트래픽 관리의 이점이 있는 것 같습니다. 대규모 환경으로의 프로젝트를 고민 중이라면 고려사항으로 넣으면 좋겠습니다.
    
     확장성 측면에서 좋을 것 같습니다. 외부 게이트웨이는 간단한 작업만을 두고 
    
     짧은 시간과 관련 지식이 부족한 점에서 구현하는데 어려움이 많았습니다. 특히 단일 서버에서의 개발 환경과 분산 환경처럼 다중 서버에서의 배포 환경에서의 네트워크 복잡도가 차이가 많이 났습니다. 프로젝트에서 처음으로 인프라를 다루다 보니 예상시간이 나오지 않았던 점과 이 부분을 구현하는데 시간을 너무 많이 할애하게 되었습니다.
    
     구현을 하면서 User서비스에서 인증과 인가를 같이 진행하도록 구성하였는데 인증 서비스의 Auth와 인가 서비스의 User로 나누고 Database를 공유하는 방식으로 진행하도록 수정해야 할 것 같습니다.
    
     도입한 2계층 게이트웨이는 하드웨어 관점에서의 개념을 가지고 접근을 한 것이어서 
    
    Spring cloud gateway의 문서를 좀더 자세히 읽어보면서 적용의 이점을 다시 확인하는 시간을 갖을 예정입니다.
    
     구현을 했지만 예상 완료 시점을 산정할 수 없어서 기한이 길어져 모니터링을 진행하지 못해 수치적인 측면에서의 결과를 내지 못 했습니다. 이 부분은 앞으로의 계획으로 잡고 마무리를 짓도록 기간을 정했습니다.
    
     
    
- 상위모듈 + 공통 모듈 + Docker-Compose
    
    
    - 도입 배경
    
     이전 프로젝트를 MSA설계 도메인 별 서비스를 진행하면서 서비스 별로 필요한 dependencies와 코드들이 중복으로 발생하였습니다. 이 중복된 코드들을 공통으로 묶어서 관리하는 방법으로 공통 모듈을 사용하는 방법으로 진행하게 되었습니다.
    
    - 구현
    
     도메인 별로 나눈 서비스와 같은 레벨에 공통 모듈을 두었습니다. 이 공통 모듈을 각 서비스에서 인식하게 하기 위해 상위 모듈에서 빌드 시 공통 모듈 프로젝트를 각 서비스의 dependencies로 추가하여 서비스에서 공통 모듈 내용을 인식하게 진행하였습니다. 이러면서 관리를 상위 모듈에서 진행하도록 하였습니다. 그리고 각 서비스에 Dockerfile을 만들어서 서비스 이미지를 만들었습니다. Docker-compose를 상위 모듈에 배치하여 Dockerfile의 상위 모듈 빌드 과정을 진행하여 서비스에 필요한 정보를 빌드하였습니다. 
    
    - 구현 후 느낀 점과 개선점
    
     이렇게 구현을 하면서 빌드의 모놀리식화였다고 느꼈습니다. 공통 부분을 묶는다는 취지는 좋았지만 MSA 환경으로 구성했다는 점에서 본다면 이 부분이 MSA 설계의 장점을 줄였던 것 같습니다. MSA 설계의 장점 중에 각 서비스를 개별로 관리하기 위해 팀 단위로 진행할 수 있다는 점과 각 서비스 별로 적절한 언어로 개발이 가능하다는 점에서 마이너스가 되었습니다.
    
     서비스가 많아지고 다양해지면 공통 모듈로 둘 수 있는 dependencies들이 줄어들거나 여러 공통 모듈을 만들어야 해서 관리 포인트를 늘리게 되는 것 같습니다. 그리고 공통으로 묶은 부분의 버전관리를 보수적으로 진행할 수 밖에 없다는 느낌도 받았습니다. 이렇게 관리에 대한 비용이 증가하게 될 것 같습니다. 이러면 팀 단위가 아니게 된다는 느낌을 받았습니다.
    
     상위 모듈에서 빌드를 진행하도록 구성을 하여 상위 모듈이 빌드가 되지 않으면 하위모듈이 빌드가 되지 않도록 구성을 하여 하나의 서비스인 것처럼 빌드가 되도록 구성이 되었습니다. gradle로 빌드를 진행하여 gradle에서 지원하는 언어로 한정 지을 수 밖에 없고 gradle에 의존적이게 되는 것 같습니다. 
    
     빌드 시에 공통부분을 중복으로 빌드 해야 한다는 점에서 개선점이 필요해 보였습니다.
    
     이를 해결하기 위해 다음 2가지를 고민해 보았습니다.
    
     우선, 공통모듈을 사용하는 방법에서 해결할 수 있는 방법으로 빌드에서 상위모듈을 만들지 않도록 진행하는 방법을 생각해보았습니다. 공통 모듈을 만들어서 라이브러리화하여 maven repository에 등록 후 필요시 가져다가 쓰는 방법이 있을 것 같습니다. 핵심은 ‘상위모듈을 만들지 않는다’입니다.
    
     나머지로, 공통모듈을 사용하지 않는 방법입니다. 이번 프로젝트를 진행 시 공통 모듈을 사용하며 느낀 점은 각 서비스는 개별로 구성되어야 한다는 점이었습니다. 그리고 이를 분산환경에서 진행하게 된다면 네트워크 측면에서 VPC로 묶어 하나의 서비스인 것처럼 동작하도록 해야 한다는 점이었습니다. 개별 서비스를 묶는 것은 네트워크 측면으로 한정 짓는 것이 자유도가 유지될 것 같습니다. 이렇게 진행되면 기존에 없던 기능을 갖는 독립적인 서비스를 확장하여 프로젝트를 확장하기에 용이할 것 같습니다.
    
     끝으로, 위처럼 해결책을 고민해 보았지만 실제로 구현을 해보아야 각 구성의 장단점을 파악할 수 있을 것 같습니다. 한번 위와 같이 구성하여 진행해보고 어떤 트레이드오프가 있는지 내용을 추가해보겠습니다. 
    
     또한, MSA 설계를 진행하기 위해서는 그에 맞는 물리적인 환경이 구성되어야 한다는 점이었습니다. 인력도 개인 이해도도 필요하다는 가정 하에서 진행되어야 한다는 점이었습니다. 규모와 상황에 맞는 설계를 하여 개발을 해야 하는 것이 중요하다고 느꼈습니다. 
    
     
    
- CI/CD구성과 ECS로의 배포
    - 구성
    
    Github Actions를 활용한 CI/CD 구성과 생성된 이미지를 AWS ECR에 배포하고 
    ECS의 태스크를 정의한 방식으로 service container에 태스크를 사용하는 방식을 사용하였습니다. 
    배포를 EC2 하나의 서버에 배포를 할까 고민을 하였지만 단순한 구조로 개발환경과 비슷한 운영체제 하나에 올리는 방법이어서 분산환경에서의 배포라는 측면에서 맞지 않다고 판단하였습니다. 
    ECS의 서비스 컨테이너당 EC2를 구성하는 방법도 있지만 현재 진행 중인 방식으로는 EC2의 환경에서의 접근하여 추가적인 상세 작업을 진행하지 않을 생각이어서 Fargate(서버리스) 방식으로 선택했습니다. 
    
    - 구현
    
     구현시 어려웠던 점은 개발 환경과 배포 환경이 달라서 기존의 코드로는 적합하지 않았습니다. 서비스 간 통신을 하기 위해 네트워크 환경도 구성해야 했습니다. 
     AWS를 처음 사용하는데 네트워크 구성에 대한 개념이 잡혀 있지 않아서 고생을 하였습니다. 
     특히, 서비스 간 통신을 위해 호스트 주소를 알아야 하는데 태스크 정의를 갱신할 때마다 태스크의 IP 주소가 계속 바뀌는 상황이어서 로드밸런서를 추가해야 했습니다. 
    로드밸런서를 추가한 서비스를 만들었을 때 health check를 수행하지 못해서 로드 밸런싱이 되지 않았습니다. 나중에 알고 보니 서비스별로 ‘/’경로에 대한 api정의가 되어 있지 않아서 로드 밸런싱 응답을 받을 수 없는 상황이었고 보안 그룹에서 가용하는 포트를 추가해서 허용하도록 진행해야 했습니다. 
    
    - 개선사항
    
     프로젝트 기간이 끝나고 개선 사항으로 AWS에서의 네트워크 부분을 정리를 하고 나서 다시 한번 네트워크를 구성해볼 예정입니다. 
    
     특히, 수정해야 하는 부분이 있는데 이는 각 서비스 컨테이너가 ECR에서 docker image를 읽어오려면 ECR로의 외부 접속이 필요한데 이 부분을 간단하게 열려면 퍼블릭 IP를 열어놓고 있어야 한다는 점입니다. 이 부분을 퍼블릭을 닫고 라우팅테이블을 만들어서 ECR과의 통신을 위한 게이트웨이를 여는 방식으로 변경해보겠습니다. 
    
    이것을 쉽게 해주는 AWS VPC 서비스의 Private Link 엔드포인트를 활용하는 방식이 있어 이 부분을 적용할 수 있는 방법을 찾아보고 적용해보겠습니다.
    

## 적용 기술

### **Development**

---

[JDK 17](https://www.notion.so/JDK-17-1842dc3ef51481adbad4c7e42f996a2d?pvs=21)

[Spring Boot 3.4.1](https://www.notion.so/Spring-Boot-3-4-1-1842dc3ef51481b9a06ef80ac4631b97?pvs=21)

[Spring Data JPA](https://www.notion.so/Spring-Data-JPA-1842dc3ef5148199932ae1f1667a8075?pvs=21)

[Spring Cloud Gateway (1)](https://www.notion.so/Spring-Cloud-Gateway-1-52d61d0bc2de448197840938f6bab517?pvs=21)

[QueryDSL](https://www.notion.so/QueryDSL-1842dc3ef51481d1945be1090511a8cb?pvs=21)

[Gradle 8.11.1](https://www.notion.so/Gradle-8-11-1-1842dc3ef51481c78c31ce5db9d26d88?pvs=21)

### Database

---

[PostgreSQL 16.4 (1)](https://www.notion.so/PostgreSQL-16-4-1-6d0c5596ff9d4fd8b2d06af8a067a45c?pvs=21)

[Redis](https://www.notion.so/Redis-1842dc3ef514811ab4aee946679ec73d?pvs=21)

[H2 (Test DB)](https://www.notion.so/H2-Test-DB-1842dc3ef51481d5bb3cdc967d2b6fc7?pvs=21)

### Server & IPC

---

[Eureka](https://www.notion.so/Eureka-1842dc3ef51481ab95aae4ab2b090e07?pvs=21)

[  `Spring Gateway`](https://www.notion.so/Spring-Gateway-1842dc3ef51481ea8c86c5a0c7077e5c?pvs=21)

[  `Apache Kafka`](https://www.notion.so/Apache-Kafka-1842dc3ef51481b68af4c71d8ce0cc5f?pvs=21)

[  `OpenFeign`](https://www.notion.so/OpenFeign-1842dc3ef51481e99b4ad4999fc503c7?pvs=21)

### Logging & Monitoring

---

[Prometheus](https://www.notion.so/Prometheus-588d80ac5de84091a870f23fbb0951e8?pvs=21)

[Grafana](https://www.notion.so/Grafana-39709306263e4fd8a26ff5b6e0f920ed?pvs=21)

[Zipkin](https://www.notion.so/Zipkin-70e673ec604c43999dc8b2469629150e?pvs=21)

### Test

---

[Junit 5](https://www.notion.so/Junit-5-7d8f017b04d0487f9963edff6be3fa53?pvs=21)

[JMeter](https://www.notion.so/JMeter-eaf34477020541f085f70f3d650b4d4f?pvs=21)

### CI/CD & Infra

---

[  `Docker`](https://www.notion.so/Docker-8bc7670a9ffa4b709d8200768bdb79b6?pvs=21)

[  `Docker Compose`](https://www.notion.so/Docker-Compose-780775b8cb924465b7e8ffc8bafbf0a0?pvs=21)

[  `Github Actions`](https://www.notion.so/Github-Actions-564d8cebd61945f0a319f5d646caaf01?pvs=21)

[  `AWS ECR`](https://www.notion.so/AWS-ECR-4dfaf72df7c046c3b30273f2aa00046a?pvs=21)

[  `AWS EC`S](https://www.notion.so/AWS-ECS-99110547401d4494a1402b76af92b24a?pvs=21)

[  `AWS RDS`](https://www.notion.so/AWS-RDS-92ea916abef34b9bb51c4ed57c9003a1?pvs=21)

## 트러블슈팅

- **대기열 fifo queue 카프카로 구현시 파티셔닝 문제**
    
    파일시스템에 메시지가 영속저장되는 카프카 특성상, 대기열 서비스 인스턴스가 다운되더라도 대기열 상황을 복구가능하단 장점이 있기에 카프카를 통해 대기열이 fifo queue를 구현하려고 시도했습니다.
    
    하지만 카프카로 fifo queue 기능 구현하려면 한 파티션에 한 예매의 대기열만 저장해야 합니다. 그렇지 않을 시 시스템의 복잡도가 매우 올라가버립니다.
    이때문에 강의 갯수 만큼 파티션 수가 있어야 하므로, 지나치게 많은 파티션 필요한 문제점이 발생합니다.
    
    결론적으로 redis를 활용하기로 했고, 영속저장의 문제는 rdb/aof기능을 활용, 처리가능한 데이터 규모가 상대적으로 작은 문제는 application level clustering / redis clustering을 적절히 활용해 해결하기로 했습니다.
    대안으로는 rabbitMQ, aws sqs의 queue기능, redis등이 있었고, rabbitMQ/aws sqs등의 방법을 사용 시 카프카 처럼 영속저장을 통해 대기열 서비스 다운에 대비할 수 있단 장점이 있었습니다. 하지만 redis의 rdb/aof기능을 적절히 활용해도 이는 해결가능한 부분이었습니다. 
    또한 다른 부분에서 redis 캐싱을 이미 써야 하는 상황이었고, 메시지 브로커를 목적에 두고 만들어진 기술을 대기열 fifo queue만을 위해 도입하는건 오버엔지니어링이라고 생각했습니다. 
    만약 처리 트래픽이 늘어나면 클러스터링을 해야하는데, 이 때문에도  샤딩방식으로 클러스터링 기능을 제공하는 redis가 더 적합하다 판단했습니다. (rabbitMQ는 클러스터링 방식)
    
    때문에 redis sorted set을  사용해 구현하는 방식을 선택함으로써 문제를 해결했습니다.
    

- **강의 CRUD 및 연계 기능 문제 해결**
    
    ### Before
    
    - **강의 CRUD 문제**:
        - 강의와 스케줄 데이터를 각각 관리하던 방식이 데이터 무결성을 저하시켰음.
        - 학생과 강의 연계 시 데이터 관계를 명확히 하지 않아 참조 무결성 오류 발생.
    - **강의 예약 문제**:
        - 강의 예약 시작 시간 데이터가 누락되어 예약과 강의 연계가 어려웠음.
    
    ### After
    
    - **강의와 스케줄 통합 관리**:
        - 강의와 강의 스케줄을 통합 설계하여 참조 무결성을 유지.
        - 스케줄 변경 시 자동으로 강의 정보에 반영되는 로직 추가.
    - **강의 예약 연계 개선**:
        - 강의 예약 시작 시간을 `Lecture` 도메인에 추가하여 예약 시 유효성 검사를 강화.
        - 예약 도메인과 강의 도메인 간 연계 테스트를 완료하여 데이터 동기화 문제 해결.
        
        **결론**: 강의, 스케줄, 예약 간 데이터 연계를 최적화하고 무결성을 강화.
        

- **쿠폰 발급 시 동시성 문제**
    - 문제 상황
    쿠폰 발급 시 다수의 사용자가 동시에 동일한 쿠폰을 요청했을 때 `issuedCount`와 `maxIssue` 조건 검사에서 Race Condition이 발생하는 문제가 있었습니다. 이에 `issuedCount`를 정확히 업데이트하지 못하거나, `maxIssue`를 초과하는 문제가 발생할 가능성이 생겼습니다.
    - 개선 방법
    동시성 문제를 해결하기 위해 `ReentrantLock`을 적용하여 발급 로직에서 Race Condition을 방지했습니다. 락을 사용하여 한 번에 하나의 요청만 발급 로직에 접근할 수 있도록 구현했습니다. 이는 애플리케이션 레벨에서 Race Condition을 제거하는 간단한 방법입니다.
        
        ```java
        lock.lock();
        try {
            if (coupon.getIssuedCount() >= coupon.getMaxIssue()) {
                throw new CustomException(CommonErrorCode.COUPON_ISSUE_LIMIT_REACHED);
            }
            coupon.incrementIssuedCount();
        } finally {
            lock.unlock();
        }
        ```
        
    - 의사결정 과정
        
         문제를 해결하기 위해 여러 방안을 검토하였습니다. 첫 번째로 고려된 방법은 Java의 `ReentrantLock`을 사용하여 발급 로직을 단일 쓰레드에서만 처리하도록 제한하는 것이었습니다. 이 방식은 구현이 간단하고, 서버 레벨에서 동시성을 제어할 수 있다는 장점이 있었습니다. 하지만 높은 트래픽 환경에서는 요청 대기가 길어질 가능성이 있어 이를 단점으로 평가했습니다.
        
         두 번째로 검토된 방법은 데이터베이스 락(`SELECT ... FOR UPDATE`)을 사용하여 트랜잭션 레벨에서 동시성을 제어하는 것이었습니다. 이는 데이터베이스 수준에서 안전한 동시성 관리가 가능하다는 점에서 장점이 있지만, 트랜잭션 락으로 인해 성능 저하가 발생할 가능성이 있어 신중한 적용이 필요했습니다.
        
         최종적으로 현재 트래픽 수준에서는 `ReentrantLock`을 사용하는 방식이 가장 적합하다고 판단하였으며, 이를 통해 빠르게 문제를 해결했습니다. 동시에, 시스템 확장과 고트래픽 환경에 대비하여 데이터베이스 락 방식도 고려할 수 있도록 구조를 설계했습니다.
        
    

- Gateway에서 Feignclient 순환 참조 에러
    - 문제 상황
    Gateway에서 권한 검사를 진행한 후 권한이 일치하면 해당 접속자를 해당 서비스로 라우팅시키는 부분에서 Gateway에서 직접 User Database에서 가져오지 않고 User 서비스에 위임하는 방식으로 진해하였습니다. 이때 처음으로 사용한 방법이 FeignClient 였습니다.
        
        ![스크린샷 2025-01-24 124320.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/83c75a39-3aba-4ba4-a792-7aefe4b07895/0da7e74e-b5d5-4c86-85d2-711f1b083709/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2025-01-24_124320.png)
        
        Gateway의 필터와 FeignClient 간에 불러오는 과정에서 AutoConfiguration에 의해 다시 Gateway를 통과하는 과정이 발생합니다. FeignClient를 통해 요청을 하면 로드밸런싱에 의해 다시 Gateway를 거치는 과정이 진행되는 현상인 것 같다.
        
    
    - 의사 결정 및 개선 방법
    RestTemplate을 사용해서 외부 api 호출을 하는 방법과 WebClient라는 비동기RestTemplate으로 진행하는 방법이 있었습니다.
    Spring Cloud Gateway가 비동기 통신으로 진행해서 WebClient로 진행하게 되었습니다.
    그런데 진행하다 보니 api 호출 후 응답을 받은 값으로 다음 단계를 진행해야 해서 동기화로 진행하도록 변경하였습니다. 이때 사용한 것이 block() 함수입니다.
    추후에 WebClient를 조금 알아보는 시간을 갖고 block()하는 방법 외에 다른 접근법이 있는지 확인하고 수정할 예정입니다.
        
        ```java
        @Slf4j
        @Service
        public class AuthClient {
        
            private final WebClient webClient;
        
            public AuthClient(WebClient.Builder webClientBuilder) {
                this.webClient = webClientBuilder.build();
            }
        
            public Mono<ResponseEntity<ApiResponse<VerifyResponse>>> validateUserExists(String userId, String userRole, String internalKey, URI uri) {
                return webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .scheme("http")
                                .host(uri.getHost())
                                .port("19020")
                                .path("/users/verify")
                                .build())
                        .header(KEY_USER_ID, userId)
                        .header(KEY_USER_ROLE, userRole)
                        .header(KEY_INTERNAL_KEY, internalKey)
                        .retrieve()
                        .toEntity(new ParameterizedTypeReference<ApiResponse<VerifyResponse>>(){});
            }
        }
        ```
        
        ```java
        ApiResponse<VerifyResponse> body = authClient.validateUserExists(userId, userRole, internalKey, request.getURI())
                            .block()
                            .getBody();
                    VerifyResponse data  = body.data();
                    if(data == null || !data.isVerified()){
                        return errorResponse(exchange, "Permission denied.");
                    }
        ```
        
    

- 캐싱 어노테이션 파라미터 에러
    - 문제 상황
    캐싱이 적용되는 어노테이션을 사용하는 과정에서 @Cacheable을 사용시 return값으로 cache에 값이 저장되어서 @Cacheable key값으로 SpEL문법이 적용하여 #UserDto.id로 넣어주었습니다. 그런데 해당 매서드로 진입이 되지 않는 에러가 발생하였습니다.
    확인해보니 key값을 진입시 찾을 수 있는 값이 아닌 에려였습니다.
        
        ![스크린샷 2025-01-24 133509.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/83c75a39-3aba-4ba4-a792-7aefe4b07895/cd6d5a5f-c2aa-47a2-8775-fe1d95071524/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2025-01-24_133509.png)
        
    
    - 개선 방법
    RedisTemplate을 사용하여 Cache에 직접 값을 넣어주는 방식으로 변경했습니다.
    이렇게 사용하면 return값으로 자동으로 Cache에 넣어주지 못하고 기존 캐시전략을 사용하지 못 하지만 원하는 값을 직접 넣어줄 수 있습니다. @Cacheable이 Cache-Aside 방식으로 진행되어서 캐시에서 확인해주고 없으면 캐시에 저장하고 durty-checking으로 transactional이 끝나는 시점에 RDB에 넣어주면 되었습니다.
        
        ```java
        @Configuration
        @EnableCaching
        public class CacheConfig {
        
            @Bean
            public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
                RedisCacheConfiguration config = RedisCacheConfiguration
                        .defaultCacheConfig()
                        .disableCachingNullValues()
                        .entryTtl(Duration.ofMinutes(5))
                        .computePrefixWith(CacheKeyPrefix.simple())
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
                RedisCacheConfiguration individual = RedisCacheConfiguration
                        .defaultCacheConfig()
                        .disableCachingNullValues()
                        .entryTtl(Duration.ofMinutes(5))
                        .enableTimeToIdle()
                        .computePrefixWith(CacheKeyPrefix.simple())
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
                return RedisCacheManager
                        .builder(connectionFactory)
                        .cacheDefaults(config)
                        .withCacheConfiguration("userCache", individual)
                        .build();
            }
        }
        ```
        
        ```java
        @Configuration
        public class RedisConfig {
        
            @Bean
            public RedisTemplate<String, UserDto> userTemplate(
                    RedisConnectionFactory redisConnectionFactory
            ) {
                RedisTemplate<String, UserDto> template = new RedisTemplate<>();
                template.setConnectionFactory(redisConnectionFactory);
                template.setKeySerializer(RedisSerializer.string());
                template.setValueSerializer(RedisSerializer.json());
                return template;
            }
        }
        ```
        
        ```java
        @Service
        public class UserService {
            private final UserRepository userRepository;
            private final RedisTemplate<String, UserDto> userRedisTemplate;
            private final ValueOperations<String, UserDto> userListOps;
        
            @Value("${service.internal.internal-key}")
            private String secretKey;
        
            public UserService(UserRepository userRepository, RedisTemplate<String, UserDto> userRedisTemplate) {
                this.userRepository = userRepository;
                this.userRedisTemplate = userRedisTemplate;
                this.userListOps = this.userRedisTemplate.opsForValue();
            }
        
            public VerifyResponse verify(String userId, UserRole userRole, String secretKey) {
                UserDto userDto = userListOps.get("userCache::"+userId);
                User user;
                if(userDto == null || userDto.isDeleted()){
                    user = userRepository.findByIdAndDeletedAtIsNull(UUID.fromString(userId))
                            .orElse(null);
                    userListOps.set("userCache::"+user.getId(), UserDto.fromEntity(user));
                }else{
                    user = User.getUserRedis(userDto);
                }
        
                boolean verified = true;
                if(user == null) {
                    return new VerifyResponse(false);
                }
                if(user.getRole() != userRole) {
                    verified = false;
                }
                if(!this.secretKey.equals(secretKey)){
                    verified = false;
                }
        
                return new VerifyResponse(verified);
            }
        }
        ```
        

## CONTRIBUTORS

| 팀원명 | 포지션 | 담당(개인별 기여점) | 깃허브 링크 |
| --- | --- | --- | --- |
| 김성용 | Reservation,
Pay/Settlement,
Queue | ▶ reservation
- 예약 crud
- 결제 기간 지난 예약 삭제
▶ Pay/Settlement
- 결제 및 정산 api
▶ Queue
- 예매 서비스, 대기열 기능 개발
- Redis 사용해 fifo queue 구현 | https://github.com/soeng-dev |
| 이서영 | Coupon | ▶ 기능 개발
- 주요 API(쿠폰 생성, 수정, 삭제, 발급, 사용, 복원) 설계 및 구현.
-
▶ 트러블 슈팅
- 동시성 문제를 ReentrantLock으로 해결해 안전한 쿠폰 발급 보장
 | https://github.com/mo9mo9 |
| 윤지용 | Lecture | ▶  강의 
-강의 crud
- 조건 검색 기능 추가
▶  스케쥴 관리
- 강의와 연계된 스케쥴 관리 기능 개발
- 스케쥴 생성,수정시 강의 정보와 동기화
-예약 시작 시간 필드를 포함하여 유효성 검사 강화
- 특정 강의에 대한 스케쥴 중복 방지 로직 구현
▶  학생 등록 및 출석 관리
-강의 참여 학생 등록 api
-출석및강의 완료 상태 업데이트 기능ㄴ
-학생  정보 강의 정보를 연결 효율적 관리
-강의 진행 상태와 연계된 학생 출석 데이터 동기화
▶  강사(튜터) 관리
-강사 등록 및 수정기능 구현
- 강사 등록한 강의 목록 조회 api  
-강사의 강의 활동 이력 조회 구현 | https://github.com/jeffyun3061 |
| 김영호 | 인증
인가
유저
인프라 구축
(CI/CD, AWS) | ▶ 인증
 - 외부 게이트웨이에서 jwt를 활용한 인증 
▶ 인가
 - 내부 게이트웨이에서 secret키를 활용한 내부 서비스 인식
 - 권한에 따른 라우팅
▶ 유저
 - 유저 crud
 - redis 사용
▶ 인프라 구축(CI/CD, AWS)
- github Actions
 - AWS ECR, ECS | https://github.com/Kim-Yeongho |

## 앞으로의 프로젝획

### **1. 강의 관리 시스템 고도화**

- **강사 대시보드 개발**:
    - 강사가 자신의 강의, 스케줄, 참여 학생 데이터를 확인할 수 있는 대시보드 추가.
    - 강의별 수익, 출석률 통계를 제공하여 관리 효율성 강화.
- **강의 및 스케줄 알림 기능**:
    - 예약된 강의와 변경된 스케줄에 대한 알림 시스템 구축.
    - 알림 전송 방식을 이메일과 푸시 알림으로 확장.

### **2. 대기열 서비스 강화**

- **Redis 클러스터링 구현**:
    - 처리량 증가에 대비해 Redis 클러스터링을 도입하여 대기열 성능 향상.
    - AOF와 RDB 전략을 최적화하여 데이터 복구 시간을 단축.
- **대기열 관리 페이지 추가**:
    - 관리자가 실시간 대기열 상태를 모니터링할 수 있는 UI 제공.

### **3. 쿠폰 관리 확장**

- **다양한 쿠폰 정책 추가**:
    - 지역별, 강의 카테고리별로 다양한 할인 정책 제공.
    - 추천인 코드로 쿠폰을 지급하는 기능 도입.
- **쿠폰 통계 페이지 개발**:
    - 발급, 사용, 복원된 쿠폰 데이터를 시각화하여 마케팅 전략 수립 지원.

### **4. 인증 및 보안 강화**

- **2계층 게이트웨이 최적화**:
    - 외부 게이트웨이와 내부 게이트웨이 간의 성능 병목 문제 해결.
    - 사용자 인증 속도 향상을 위한 JWT 최적화.
- **권한 관리 시스템 개선**:
    - 관리자, 강사, 학생 권한별 접근 가능한 페이지 및 기능을 세분화.

### **5. 기술 인프라 고도화**

- **AWS 네트워크 최적화**:
    - 퍼블릭 IP 노출을 최소화하고 VPC를 활용한 네트워크 보안 강화.
    - ECR과 ECS 통신에 Private Link 엔드포인트를 활용.
- **CI/CD 개선**:
    - GitHub Actions 워크플로우를 최적화하여 배포 시간을 단축.
    - 환경별 설정 자동화를 통해 개발/운영 효율성 증대.

### **6. 모니터링 시스템 구축**

- **Prometheus와 Grafana를 활용한 실시간 모니터링**:
    - 서비스 별 요청 처리 속도, 오류 비율, 서버 상태를 모니터링.
    - 장애 발생 시 알림을 통해 빠른 대응 가능하도록 시스템 설계.

### **7. 테스트 및 최적화**

- **JMeter를 활용한 부하 테스트**:
    - 동시 사용자 증가에 대비한 강의, 예약, 대기열 서비스 부하 테스트 진행.
    - 성능 병목 구간 분석 후 최적화 작업 수행.
- **통합 테스트 강화**:
    - 모든 도메인 간 연계 기능에 대한 통합 테스트 추가.
    - 테스트 자동화 도구를 통해 배포 전 에러 발생률 최소화.

### **8. 사용자 경험 개선**

- **사용자 친화적 UI/UX**:
    - 강사, 학생, 관리자를 위한 맞춤형 인터페이스 설계.
    - 검색 필터 강화 및 예약 과정 간소화.
- **다국어 지원**:
    - 영어 및 기타 언어 지원을 통해 글로벌 시장 진출 대비.
