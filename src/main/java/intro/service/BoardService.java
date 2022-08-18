package service;

import domain.board.BoardEntity;
import domain.board.BoardRepository;
import domain.name.NameRepository;
import dto.BoardDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private NameRepository nameRepository;

    @Transactional
    public boolean save(BoardDto boardDto){
        int bno = ((BoardEntity)this.boardRepository.save(boardDto.toentity())).getBno();
        return  bno >= 1;
    }

    public JSONArray getboardlist( int cno ,String key , String keyword , int page ){

        JSONArray jsonArray = new JSONArray();
        List<BoardEntity> boardEntities = this.boardRepository.findAll();
        Iterator var3 = boardEntities.iterator();

        while(var3.hasNext()) {
            BoardEntity entity = (BoardEntity)var3.next();
            JSONObject object = new JSONObject();
            object.put("bno", entity.getBno());
            object.put("address", entity.getAddress());
            object.put("academy", entity.getAcademy());
            object.put("major", entity.getMajor());

            jsonArray.put(object);
        }

        return jsonArray;

    }

    public JSONObject getboard(int bno) {
        Optional<BoardEntity> optional = this.boardRepository.findById(bno);
        BoardEntity entity = (BoardEntity)optional.get();
        JSONObject object = new JSONObject();

        object.put("bno", entity.getBno());
        object.put("address", entity.getAddress());
        object.put("academy", entity.getAcademy());
        object.put("major", entity.getMajor());
        return object;
    }

    @Transactional
    public boolean update(BoardDto boardDto) {
        Optional<BoardEntity> optionalBoard = this.boardRepository.findById(boardDto.getBno());
        BoardEntity boardEntity = (BoardEntity)optionalBoard.get();
        boardEntity.setAddress(boardDto.getAddress());
        boardEntity.setAcademy(boardDto.getAcademy());
        boardEntity.setMajor(boardDto.getMajor());

        return true;
    }

    @Transactional
    public boolean delete(int bno) {
        BoardEntity boardEntity = (BoardEntity)this.boardRepository.findById(bno).get();
        this.boardRepository.delete(boardEntity);
        return true;
    }


}
