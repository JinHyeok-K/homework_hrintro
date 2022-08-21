package intro.domain.board;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name="info_table")
public class BoardEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno;

    private String bname;
    private String baddress;
    private String bacademy;
    private String bmajor;



}
