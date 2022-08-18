package domain.board;

import domain.name.NameEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="info_table")
public class BoardEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno;
    private String address;
    private String academy;
    private String major;


    @ManyToOne
    @JoinColumn(name = "name_no")
    private NameEntity nameEntity;

}
