create or replace view vw_employee
(empno,emp_name,title_no,title_name,manager,mgr_name,salary,dept_no,dept_name,floor)
as 
select e.empno ,e.empname,e.title,t.tname ,e.manager,m.empname ,e.salary ,e.dept ,d.deptName ,d.floor
from employee e join title t on e.title = t.tno
	left join employee m on e.manager =m.empno
	join department d on e.dept = d.deptno;


-- 새 뷰

create or replace view vw_full_employee
as
select  e.empno, 
	e.empname, 
	t.tno as title_no,
	t.tname as title_name,
	e.manager as manager_no,
	m.empname as manager_name,
	e.salary, 
	d.deptno,
	d.deptname,
	d.floor 
from employee e join title t on e.title = t.tno
left join employee m on e.manager = m.empno
join department d on e.dept = d.deptno;
	

select * from title;
-- 해달 직책을 가지고 있는 사원목록을 검색

select  empname, empno 
from employee e  
join title t
	on e.title = t.tno   -- ; 
	where tno = 5;
-- 
select  empname, empno from employee e  join title t on e.title = t.tno   where tno = 5;
-- 해당 직책별 사원수

select  tname, count(*) as 사원수
from title t left join employee e on t.tno = e.title
group by tno;


select * from empl

-- 해당 부서의 사원목록 검색

select empno, empname from employee e
join department d  
	on e.dept  =d.deptNo 
	where deptNo = 4;


-- 수업

select * from employee  where empno =1003; 
select  empno,empname,title_no,title_name,manager_no, manager_name,salary,deptno,deptname,floor
 from vw_full_employee;
 

-- password 길이 확인하기 (단방향 해시함수를 이용해서 암호화할거임) 해시함수를 사용하면 길이에 상관없이 41바이트로 돌려준다.
-- 해시함수이기때문에 단방향함수이다.  결과값은 동일하지만 찾아가기가힘들다 그래서 많이쓴다. 좀더 좋은방법도있겠지만 이건 옛날방법이당. 
-- (Hash :MD5)이다 .
-- 비교는 어떻게??
select password('aaa'),length (password('aaaaaaaaa'))  from dual; -- aaa라는 글자를 패스워드로 암호화해서 보여달라

 -- 비교는 어떻게?? 외부에서 던져주면 여기에서 비교해서 
 select  password('1234')
 
 -- 
insert into emp_detail(empno,pic,gender,hiredate,pass 
values(?,?,?,?, password(?)));


-- emp_detail insert
insert into emp_detail(empno, pic , gender, hiredate, pass)
values(?, ?, ?, ?, ?);

select * from emp_detail;



delete * from emp_detail where empno = 1003;
