package intro.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import intro.domain.board.BoardEntity;

import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardDto {

    private int bno;

    private String bname;
    private String baddress;
    private String bacademy;
    private String bmajor;



    public BoardEntity toentity(){
        return BoardEntity.builder()
                .bno( this.bno )
                .bname(this.bname)
                .baddress( this.baddress)
                .bacademy( this.bacademy)
                .bmajor( this.bmajor)
                .build();
    }

}
