package br.com.guifroes1984.ecom.entity;

import br.com.guifroes1984.ecom.enums.UsuarioRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String email;
    
    private String password;

    private String nome;

    private UsuarioRole role;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

}
