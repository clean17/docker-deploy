package com.example.springbootserver.testModel;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootserver.core.dto.ResponseDTO;



@RestController
public class TestController {

    private final TstableRepository repository;

    public TestController(TstableRepository repository){
        this.repository = repository;
    }
 
//    @Value("${meta.host}")
//    private String host;   
//    @Value("${meta.port}")
//    private String port;
//    @Value("${meta.db}")
//    private String db;
//    @Value("${meta.user}")
//    private String user;
//    @Value("${meta.pass}")
//    private String pass;

   @GetMapping("/test")
   public String tt(){
    //    return "host : " + host +"\n"+ " port : " + port + "\n" + "db : " + db + "\n" + "user : " + user + "\n" + "pass : " + pass ;
    return  
        "host : " + System.getenv("MYSQL_HOST") + "\n" +
        "database : " + System.getenv("MYSQL_DATABASE") + "\n" +
        "USER : " + System.getenv("MYSQL_USER") + "\n";
   }

    @GetMapping("/ts")
    public ResponseEntity<?> findAll() {
        List<Tstable> entityList = repository.findAll();

        return new ResponseEntity<>(new ResponseDTO<>(200, "조회 성공", entityList), HttpStatus.OK);
        // return ResponseEntity.ok().body(response);
    }
    
    @GetMapping("/")
    public String healthCheck() {
        return "엘라스틱 빈스톡 정상 + 서버 실행중 ..";
    }
}
