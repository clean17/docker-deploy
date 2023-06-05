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
 
    // @Value("${secret.key}") // prod
//    @Value("${meta.port}")
//    private String port;
//    @Value("${meta.db}")
//    private String db;
//    @Value("${meta.user}")
//    private String user;
//    @Value("${meta.pass}")
//    private String pass;
//
//    @GetMapping("/test")
//    public String tt(){
//
//        return "port : " + port + "\n" + "db : " + db + "\n" + "user : " + user + "\n" + "pass : " + pass ;
//    }

    @GetMapping("/ts")
    public ResponseEntity<?> findAll() {
        List<Tstable> entityList = repository.findAll();

        return new ResponseEntity<>(new ResponseDTO<>(200, "조회 성공", entityList), HttpStatus.OK);
        // return ResponseEntity.ok().body(response);
    }
}
