package intro.service;

import intro.domain.board.BoardEntity;
import intro.domain.board.BoardRepository;

import intro.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {



    @Autowired  // 자동 빈 생성 [ 자동 생성자 이용한 객체에 메모리 할당 ]
    private BoardRepository boardRepository;
    @Autowired
    private HttpServletRequest request;

//    @Autowired
//    private BoardService boardService;

    // 저장
    @Transactional
    public boolean save(BoardDto boardDto){
        BoardEntity boardEntity = boardDto.toentity();
//        BoardEntity boardEntity = boardRepository.save(boardDto.toentity()));
        boardRepository.save(boardEntity);
        return true;
    }

    // 전체 읽어오기

    public JSONObject getboardlist( String key , String keyword , int page  ){

        JSONObject object = new JSONObject();

        Page<BoardEntity> boardEntities = null ; // 선언만

        // Pageable : 페이지처리 관련 인테페이스
        // PageRequest : 페이징처리 관련 클래스
        // PageRequest.of(  page , size ) : 페이징처리  설정
        // page = "현재페이지"   [ 0부터 시작 ]
        // size = "현재페이지에 보여줄 게시물수"
        // sort = "정렬기준"  [   Sort.by( Sort.Direction.DESC , "정렬필드명" )   ]
        // sort 문제점 : 정렬 필드명에 _ 인식 불가능 ~~~  ---> SQL 처리
        Pageable pageable = PageRequest.of( page , 5 , Sort.by( Sort.Direction.DESC , "bno")    ); // SQL : limit 와 동일 한 기능처리
        System.out.println("pageable:"+pageable);
        // 필드에 따른 검색 기능

        if(  key.equals("bname") ){
            boardEntities = boardRepository.findBybname( keyword , pageable );
        }else {
            boardEntities = boardRepository.findBybno( keyword , pageable );
        }

//        boardEntities = boardRepository.findBybname( keyword , pageable );

        System.out.println( "검색된 게시물 정보 : "+boardEntities);

        // 페이지에 표시할 총 페이징 버튼 개수
        int btncount =3;
        // 시작번호버튼 의 번호      [   ( 현재페이지 / 표시할버튼수 ) * 표시할버튼수 +1

        int startbtn  = ( page / btncount ) * btncount + 1;
        // 끝 번호버튼의 번호       [  시작버튼 + 표시할버튼수-1 ]

        int endhtn = startbtn + btncount -1;

        // 만약에 끝번호가 마지막페이지보다 크면 끝번호는 마지막페이지 번호로 사용
        if( endhtn > boardEntities.getTotalPages() ) endhtn = boardEntities.getTotalPages();

        // 엔티티 반환타입을 List 대신 Page 인터페이스 할경우에
//        System.out.println( "검색된 총 게시물 수 : "  + boardEntities.getTotalElements() );
//           System.out.println( "검색된 총 페이지 수 : " + boardEntities.getTotalPages() );
//        System.out.println( "검색된 게시물 정보 : " + boardEntities.getContent() );
//        System.out.println( "현재 페이지수 : " + boardEntities.getNumber() );
//        System.out.println( "현재 페이지의 게시물수 : " + boardEntities.getNumberOfElements() );
//        System.out.println( "현재 페이지가 첫페이지 여부 확인  : " +  boardEntities.isFirst() );
//        System.out.println( "현재 페이지가 마지막 페이지 여부 확인  : " +  boardEntities.isLast() );

        //*  data : 모든 엔티티 -> JSON 변환
        JSONArray jsonArray = new JSONArray();

        for( BoardEntity entity : boardEntities ){

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bno", entity.getBno());
            jsonObject.put("bname", entity.getBname());
            jsonObject.put("baddress", entity.getBaddress());
            jsonObject.put("bacademy", entity.getBacademy());
            jsonObject.put("bmajor", entity.getBmajor());

            jsonArray.put(jsonObject);
        }

        // js 보낼 jsonobect 구성
        object.put( "startbtn" , startbtn );       //  시작 버튼
        object.put( "endhtn" , endhtn );         // 끝 버튼
        object.put( "totalpages" , boardEntities.getTotalPages() );  // 전체 페이지 수
        object.put( "data" , jsonArray );  // 리스트를 추가

        return object;
    }


    // 2. R : 개별조회 [ 게시물번호 ]
    @Transactional
    public JSONObject getboard( int bno ){

        // 조회수 증가처리 [ 기준 : ip / 24시간 ]
        String ip = request.getRemoteAddr();    // 사용자의 ip 가져오기

        Optional<BoardEntity> optional =  boardRepository.findById( bno );
        BoardEntity entity = optional.get();

        // 세션 호출
        Object com =  request.getSession().getAttribute(ip+bno);
        if( com == null  ){ // 만약에 세션이 없으면
            // ip 와 bno 합쳐서 세션(서버내 저장소) 부여
            request.getSession().setAttribute(ip+bno , 1 );
            request.getSession().setMaxInactiveInterval( 60*60*24  ); // 세션 허용시간 [ 초단위  ]
            // 조회수 증가처리

        }

        JSONObject object = new JSONObject();
        object.put("bno" , entity.getBno() );
        object.put("bname" , entity.getBname() );
        object.put("baddress" , entity.getBaddress() );
        object.put("bacademy" , entity.getBacademy() );
        object.put("bmajor" , entity.getBmajor() );
        return object;
    }

    @Transactional
    public boolean update(BoardDto boardDto) {
        System.out.println("boardDto"+boardDto);
        Optional<BoardEntity> optionalBoard = boardRepository.findById(boardDto.getBno());
        BoardEntity boardEntity = optionalBoard.get();

        boardEntity.setBname(boardDto.getBname());
        boardEntity.setBaddress(boardDto.getBaddress());
        boardEntity.setBacademy(boardDto.getBacademy());
        boardEntity.setBmajor(boardDto.getBmajor());


        return true;
    }

    @Transactional
    public boolean delete(int bno) {
        BoardEntity boardEntity = (BoardEntity)this.boardRepository.findById(bno).get();
        this.boardRepository.delete(boardEntity);
        return true;
    }


}
