package com.example.fileuploaddownload.entity;

import com.example.fileuploaddownload.model.UploadStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Transactional
@AllArgsConstructor
@NoArgsConstructor
public class FileTable {
    @Id
    @SequenceGenerator(
            name = "file_id_seq",
            sequenceName = "file_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "file_id_seq"
    )
    private long id;
    private String contentType;
    private String location;
    @Enumerated
    private UploadStatus status = UploadStatus.PENDING;
    private String cdnUrl;
    private long size;
}
