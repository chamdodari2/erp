-- 내 스키마
DROP SCHEMA IF EXISTS erp;

-- 내 스키마
CREATE SCHEMA erp;

-- 세부정보
CREATE TABLE erp.emp_detail (
	empno   INT(11)    NOT NULL COMMENT '사원번호', -- 사원번호
	pic     LONGBLOB   NULL     COMMENT '증명사진', -- 증명사진
	gender  TINYINT(1) NULL     COMMENT '성별', -- 성별
	hiedate DATETIME      NULL     COMMENT '입사일', -- 입사일
	pass    CHAR(41)   NULL     COMMENT '비밀번호' -- 비밀번호
)
COMMENT '세부정보';

-- 세부정보
ALTER TABLE erp.emp_detail
	ADD CONSTRAINT PK_emp_detail -- 세부정보 기본키
		PRIMARY KEY (
			empno -- 사원번호
		);

-- 부서
CREATE TABLE erp.department (
	deptNo   INT(11)     NOT NULL COMMENT '부서번호', -- 부서번호
	deptName VARCHAR(20) NOT NULL COMMENT '부서명', -- 부서명
	floor    INT(11)     NULL     COMMENT '위치' -- 위치
)
COMMENT '부서';

-- 부서
ALTER TABLE erp.department
	ADD CONSTRAINT
		PRIMARY KEY (
			deptNo -- 부서번호
		);

-- 직책
CREATE TABLE erp.title (
	tno   INT(11)     NOT NULL COMMENT '직책코드', -- 직책코드
	tname VARCHAR(20) NOT NULL COMMENT '직책명' -- 직책명
)
COMMENT '직책';

-- 직책
ALTER TABLE erp.title
	ADD CONSTRAINT
		PRIMARY KEY (
			tno -- 직책코드
		);

-- 학생
CREATE TABLE erp.student (
	stdNo   INT(11)    NOT NULL COMMENT '학번', -- 학번
	stdName VARCHAR(5) NOT NULL COMMENT '성명', -- 성명
	kor     INT(11)    NULL     COMMENT '국어', -- 국어
	eng     INT(11)    NULL     COMMENT '영어', -- 영어
	math    INT(11)    NULL     COMMENT '수학' -- 수학
)
COMMENT '학생';

-- 학생
ALTER TABLE erp.student
	ADD CONSTRAINT
		PRIMARY KEY (
			stdNo -- 학번
		);

-- 사원
CREATE TABLE erp.employee (
	empno   INT(11)     NOT NULL COMMENT '사원번호', -- 사원번호
	empname VARCHAR(20) NULL     COMMENT '사원명', -- 사원명
	title   INT(11)     NULL     COMMENT '직책', -- 직책
	manager INT(11)     NULL     COMMENT '직속상사', -- 직속상사
	salary  INT(11)     NULL     COMMENT '급여', -- 급여
	dept    INT(11)     NULL     COMMENT '부서' -- 부서
)
COMMENT '사원';

-- 사원
ALTER TABLE erp.employee
	ADD CONSTRAINT
		PRIMARY KEY (
			empno -- 사원번호
		);

-- 사원
ALTER TABLE erp.employee
	ADD CONSTRAINT FK_department_TO_employee -- FK_department_TO_employee
		FOREIGN KEY (
			dept -- 부서
		)
		REFERENCES erp.department ( -- 부서
			deptNo -- 부서번호
		),
	ADD INDEX FK_department_TO_employee (
		dept -- 부서
	);

-- 사원
ALTER TABLE erp.employee
	ADD CONSTRAINT FK_employee_TO_employee -- FK_employee_TO_employee
		FOREIGN KEY (
			manager -- 직속상사
		)
		REFERENCES erp.employee ( -- 사원
			empno -- 사원번호
		),
	ADD INDEX FK_employee_TO_employee (
		manager -- 직속상사
	);

-- 사원
ALTER TABLE erp.employee
	ADD CONSTRAINT FK_title_TO_employee -- FK_title_TO_employee
		FOREIGN KEY (
			title -- 직책
		)
		REFERENCES erp.title ( -- 직책
			tno -- 직책코드
		),
	ADD INDEX FK_title_TO_employee (
		title -- 직책
	);

-- 세부정보
ALTER TABLE erp.emp_detail
	ADD CONSTRAINT FK_employee_TO_emp_detail -- 사원 -> 세부정보
		FOREIGN KEY (
			empno -- 사원번호
		)
		REFERENCES erp.employee ( -- 사원
			empno -- 사원번호
		);