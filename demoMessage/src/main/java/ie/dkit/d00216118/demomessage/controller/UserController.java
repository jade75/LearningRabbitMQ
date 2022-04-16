package ie.dkit.d00216118.demomessage.controller;

import ie.dkit.d00216118.demomessage.dto.UserLoginDto;
import ie.dkit.d00216118.demomessage.response.BaseResponse;
import ie.dkit.d00216118.demomessage.response.StatusCode;
import ie.dkit.d00216118.demomessage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private static final String prefix="user";

    @RequestMapping(value = prefix+"/login",method = RequestMethod.POST)
    public BaseResponse login(@RequestBody @Validated UserLoginDto dto, BindingResult result){
        if (result.hasErrors()){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            Boolean res=userService.login(dto);
            if (res){
                response=new BaseResponse(StatusCode.Success.getCode(),"登录成功");
            }else{
                response=new BaseResponse(StatusCode.Fail.getCode(),"登录失败-账户名密码不匹配");
            }
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }
}
