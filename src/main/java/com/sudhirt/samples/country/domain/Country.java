package com.sudhirt.samples.country.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Country implements Serializable {

    private static final long serialVersionUID = -8680218145560117467L;

    @Id
    private String iso3;
    @Column(nullable = false)
    @NotBlank
    private String iso2;
    @Column(nullable = false)
    @NotBlank
    private String numericCode;
    @Column(nullable = false)
    @NotBlank
    private String name;
}
