package com.iobuilders.user.infrastructure.persistence;

import com.iobuilders.wallet.infrastructure.persistence.WalletEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "users")
@Table(name = "users")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(nullable = false, unique = true)
    private String userName;

    @Column
    @NotNull
    private String password;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String surname;

    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = true)
    private WalletEntity wallet;

}
