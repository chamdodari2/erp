-- 계정권한부여

grant all
on erp.*
to 'user_erp'@'localhost' identified by 'rootroot';

-- file 권한 (백업, 로드) 루트 구ㅕㅓㄴ한만 부여가능
grant File
on *.*
to 'user_erp'@'localhost';