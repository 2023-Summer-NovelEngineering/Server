package dgsw.hs.kr.memo.entity;

import dgsw.hs.kr.memo.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {

    @Id
    private String memberId;

    @NotNull
    @Column(length = 20)
    private String passwd;

    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

}
