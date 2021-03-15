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