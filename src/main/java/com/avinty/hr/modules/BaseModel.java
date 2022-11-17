package com.avinty.hr.modules;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@MappedSuperclass
public abstract class BaseModel {

    @Id
    @Column(name = "id")
    private Integer id;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    @CreatedDate
    private Date createdAt;

    @Column(name="created_by")
    @CreatedBy
    private Integer createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modified_at")
    @LastModifiedDate
    private Date modifiedAt;

    @Column(name="modified_by")
    @LastModifiedBy
    private Integer modifiedBy;
}
