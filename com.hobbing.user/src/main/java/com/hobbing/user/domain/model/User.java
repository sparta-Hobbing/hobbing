package com.hobbing.user.domain.model;

<<<<<<< HEAD
import com.hobbing.user.presentation.dto.PutUserReqDto;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;

=======
import com.hobbing.common.domain.model.BaseEntity;
import com.hobbing.user.presentation.dto.PutUserReqDto;
import jakarta.persistence.*;
import lombok.*;
>>>>>>> dev
import java.time.LocalDateTime;
import java.util.UUID;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "p_user")
public class User extends BaseEntity {

    private static final String REMOVED_HYPHEN = "-";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;    // 회원 아이디 역할

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "profile")
    private String profile;

    @Column(name = "phone_number", nullable = false)
    private String phone_number;

    private static String removeHyphen(String phone_number) {
        return phone_number.replaceAll(REMOVED_HYPHEN, "");
    }

    public static User create(
        String nickname, String name, String email, String password,
        UserRole role, String profile, String phone_number
    ) {
        return User.builder()
                .nickname(nickname)
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .profile(profile)
                .phone_number(removeHyphen(phone_number))
                .build();
    }

    public void modifyUser(PutUserReqDto dto){
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.profile = dto.getProfile();
        this.phone_number = removeHyphen(dto.getPhoneNumber());
    }

    public void modifyUserRole(UserRole role) {
        this.role = role;
    }

    public void delete(){
        super.deletedAt = LocalDateTime.now();
        super.isDeleted = true;
        super.deletedBy = this.getId();
    }

}
