package com.example.seguranca.controller;

import com.example.seguranca.modal.User;
import com.example.seguranca.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService service;

    @ApiOperation(value="Faz a requisição de 15 parametros paginados da tabela usuário")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseEntity findBy(
        @RequestBody(required = false) User user,
        @RequestParam(value = "page", defaultValue = "1") Integer page,
        @RequestParam(value = "size", defaultValue = "15") Integer size
    ) {
        return ResponseEntity.ok(service.pageFindBy(user, page, size));
    }

    @ApiOperation(value="Faz a requisição de todos os dados da tabela usuário")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity findAll() {
        return ResponseEntity.ok(service.findAllRest());
    }

    @ApiOperation(value="Faz a requisição da tabela aplicação")
    @RequestMapping(value = "/app/{app}", method = RequestMethod.POST)
    public ResponseEntity findBy(@PathVariable("app") Long app, @RequestBody(required = false) User user) {
        return ResponseEntity.ok(service.findByApplication(app, user));
    }

    @ApiOperation(value = "Retorna usuário buscando pelo id", response = Response.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Retorna um usuário solicitado"),
            @ApiResponse(code = 400, message = "Falha ao processar a solicitação")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @ApiOperation(value = "Salva o usuário informado", response = Response.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Retorna um usuário solicitado"),
            @ApiResponse(code = 400, message = "Falha ao processar a solicitação")
    })
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody User user) {
        return ResponseEntity.ok(service.save(user));
    }

    @ApiOperation(value = "Atualiza o usuário informado", response = Response.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Retorna o usuário atualizado"),
            @ApiResponse(code = 400, message = "Falha ao processar a solicitação")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody User user) {
        return ResponseEntity.ok(service.update(id, user));
    }

    @ApiOperation(value = "Remove o usuário baseado no id informado", response = Response.class)
    @ApiResponses({
            @ApiResponse(code = 204, message = "usuário foi removido com sucesso"),
            @ApiResponse(code = 400, message = "Falha ao processar a solicitação")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
