package intro.controller;

import intro.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Getter @AllArgsConstructor @NoArgsConstructor
@Setter
public class IndexController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/")        // "/" 최상위 경로

    public String index( ){

        return "index"; // HTML 파일명
    }
}
