
SET SQL_SAFE_UPDATES=0;

update company
set companyType = 'PRODUCT';

ALTER TABLE jobposting AUTO_INCREMENT = 1;

idset @counter = 0;
update jobposting
set id  = (@counter := @counter + 1);

update jobposting
set employmentType = 'TEMPORARY' where employmentType = 'Temporary' ;

update jobposting
set employmentType = 'PARTTIME' where employmentType = 'Part-time' ;

update jobposting
set employmentType = 'FULLTIME' where employmentType = 'Full-time' ;

update jobposting
set employmentType = 'CONTRACT' where employmentType = 'Contract' ;

update jobposting
set employmentType = 'INTERN' where employmentType = 'Internship' ;

select * from jobposting where jobtitle =  'ServiceNow Developer- Sr. Consultant';

select * from jobposting where company_id is null;

select * from company where id = 3321;

truncate table job_required_skill;

select * from company where name is null;
select * from jobposting where company_id is not null;
