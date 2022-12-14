package intro.controller;


import intro.domain.board.BoardEntity;
import intro.domain.board.BoardRepository;
import intro.dto.BoardDto;
import intro.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    @Autowired  // 자동 빈 생성 [ 자동 생성자 이용한 객체에 메모리 할당 ]
    private BoardRepository boardRepository;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private BoardService boardService;


    //////////////////////////////////////// 1. view 열기[ 템플릿 연결 ] 매핑 //////////////////
    // 1. 게시판 페이지 열기
//    @GetMapping("/list")
//    public String list(){ return "board/list";}
    // 2. 게시물 개별 조회 열기
    @GetMapping("/view/{bno}")
    public String view( @PathVariable("bno") int bno ) {        // { } 안에서 선언된 변수는 밖에 사용불가
        // 1. 내가 보고 있는 게시물의 번호를 세션 저장
        request.getSession().setAttribute("bno", bno);
        return "board/view";
    }
    // 3. 게시물 수정 페이지 열기
    @GetMapping("/update")
    public String update(){ return "board/update";}
    // 4. 게시물 쓰기 페이지 열기
    @GetMapping("/save")
    public String save() { return  "board/save"; }


    /////////////////////////////////////// 2. intro.service 처리 매핑 ///////////////////////////////////////
    // 1. C : 게시물 저장 메소드
    @PostMapping("/save")
    @ResponseBody   // 템플릿 아닌 객체 반환
    public boolean save(BoardDto boardDto ){
//        System.out.println("우선왔다");
        System.out.println("boardDto:"+boardDto);
        return boardService.save( boardDto );
    }
    // 2. R : 모든 게시물 출력 메소드
    @GetMapping("/getboardlist")
    @ResponseBody
    public void getboardlist(
            HttpServletResponse response ,
            @RequestParam("key") String key ,
            @RequestParam("keyword") String keyword ,
            @RequestParam("page") int page  ){

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println(boardService.getboardlist(  key , keyword , page ));
        }catch( Exception e ){ System.out.println( e ); }
    }

    // 2. R2 개별 조회 출력 메소드
    @GetMapping("/getboard")
//    @RequestMapping("/getboard")
    @ResponseBody
    public void getboard( @RequestParam("bno")int bno,HttpServletResponse response){
//        int bno =  (Integer) request.getSession().getAttribute("bno");
        System.out.println("aa: "+ request.getSession().getAttribute("bno") );
//        public boolean delete( @RequestParam("bno") int bno ){
        try {
        JSONObject Object = boardService.getboard(bno);

//            int bno =  (Integer) request.getSession().getAttribute("bno");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print((  Object   ));
//            response.getWriter().print(boardService.getboard(  bno   ));
        }catch( Exception e ){
            System.out.println( e );
        }
    }



    // 3. U : 수정 메소드
    @PutMapping("/update")
    @ResponseBody
    public boolean update( HttpServletResponse response, BoardDto boardDto ){
        System.out.println("11:"+boardDto);
        int bno =  boardDto.getBno();

        System.out.println("update bno:"+bno );
        boardDto.setBno( bno );
        return boardService.update( boardDto );
    }

//    // 3. U : 수정 메소드
//    @PutMapping("/update")
//    @ResponseBody
//    public boolean update( BoardDto boardDto ){
//        int bno =  (Integer) request.getSession().getAttribute("bno");
//        System.out.println("update bno:"+bno );
//        boardDto.setBno( bno );
//        return boardService.update( boardDto );
//    }

    // 4. D : 삭제 메소드
    @DeleteMapping("/delete")
    @ResponseBody
    public boolean delete( @RequestParam("bno") int bno ){
        return boardService.delete( bno );
    }



}
