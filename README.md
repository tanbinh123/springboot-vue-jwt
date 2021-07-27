# 数据库

```sql
CREATE TABLE publictest.jwt_user (
	id varchar(50) NOT NULL COMMENT '用户id',
	username varchar(10) NOT NULL COMMENT '用户名',
	password varchar(10) NOT NULL COMMENT '密码'
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci
COMMENT='jwt用户表';

SELECT * FROM jwt_user;

CREATE TABLE publictest.jwt_token (
	id varchar(50) NOT NULL COMMENT 'id',
	token varchar(500) NOT NULL COMMENT 'token',
	last_login_time date NOT NULL COMMENT '最后登录时间',
	is_remember boolean NOT NULL COMMENT '是否记住'
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci
COMMENT='jwt临时数据表';

SELECT * FROM jwt_token;
```

