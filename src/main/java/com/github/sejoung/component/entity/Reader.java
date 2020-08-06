package com.github.sejoung.component.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "TB_READER")
@Entity
@ToString
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long key;

    @Column(name = "data")
    private String data;

    @Enumerated(EnumType.STRING)
    @Column(name = "sync_yn", length = 1)
    private SynchronizeType synchronizeType;

    @Column(name = "sync_error_msg")
    private String synchronizeErrorMessage;

    @Column(name = "sync_date")
    private LocalDateTime synchronizeDateTime;

    @Column(name = "reg_dt")
    private LocalDateTime registerDateTime;


    public Reader(String data) {
        this.data = data;
        this.synchronizeType = SynchronizeType.N;
        this.registerDateTime = LocalDateTime.now();
    }

    public void synchronizeSuccess() {
        this.synchronizeDateTime = LocalDateTime.now();
        this.synchronizeType = SynchronizeType.Y;
    }

    public void synchronizeFail(String synchronizeErrorMessage) {
        this.synchronizeErrorMessage = synchronizeErrorMessage;
        this.synchronizeDateTime = LocalDateTime.now();
        this.synchronizeType = SynchronizeType.N;
    }

    private enum SynchronizeType {
        N, Y
    }

}
