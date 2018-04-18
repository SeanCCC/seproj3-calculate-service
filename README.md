# seproj3
## steps
* 使用http://localhost:8080/login?token={token}登入
    * 如果沒有token 得去oauth service要token
* 登入成功後即可使用服務  http://localhost:8080/login?exp={exp}
* 要登出則需要帶著token去oauth server註銷token

## services
* /login 登入，要帶上token
* /logout 登出
* /infixEval 計算infix expression，要帶上算式