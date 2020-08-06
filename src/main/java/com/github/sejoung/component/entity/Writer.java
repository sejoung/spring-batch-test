package com.github.sejoung.component.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table(name = "TB_WRITER")
@Entity
@ToString
public class Writer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long key;

    @Column(name = "reader_data")
    private String readerData;

    @Column(name = "reader_key")
    private Long readerKey;

    @Column(name = "reg_dt")
    private LocalDateTime registerDateTime;


    public Writer(String readerData, Long readerKey) {
        this.readerData = readerData;
        this.readerKey = readerKey;
        this.registerDateTime = LocalDateTime.now();
    }
}
