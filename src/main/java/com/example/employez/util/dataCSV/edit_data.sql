SET SQL_SAFE_UPDATES=0;

update company
set companyType = 'PRODUCT';



update jobposting
set employmentType = 'TEMPORARY' where employmentType = 'Temporary' ;

update jobposting
set employmentType = 'PARTTIME' where employmentType = 'Part-time' ;

update jobposting
set employmentType = 'FULLTIME' where employmentType = 'Full-time' ;

update jobposting
set employmentType = 'CONTRACT' where employmentType = 'Contract' ;