package com.codelogic.cityconnect.model;

import com.codelogic.cityconnect.exception.BusinessExcetion;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] base64Data;

    public Foto(byte[] base64Data){
        setBase64Data(base64Data);
    }

    public void setBase64Data(byte[] base64Data) {
        int maxSizeInBytes = 15 * 1024 * 1024; // 15MB em bytes
        if (base64Data.length > maxSizeInBytes) {
            throw new BusinessExcetion("O tamanho do arquivo excede o limite de 15MB.");
        }
        this.base64Data = base64Data;
    }
}
