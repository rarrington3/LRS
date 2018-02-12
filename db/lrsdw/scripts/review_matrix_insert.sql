create proc sp_load_review_matrix
as

truncate table REVIEW_MATRIX

insert into REVIEW_MATRIX
(REVIEW_REFERENCE_ID,
 CASE_NUMBER,
 SELECTION_DT,
 FISCAL_YEAR,
 FISCAL_QTR,
 MTGEE5,
 INITIAL_UNACCPT_CNT,
 INITIAL_DEFICIENT_CNT,
 INITIAL_CONFORMING_CNT,
 CURRENT_UNACCPT_CNT,
 CURRENT_DEFICIENT_CNT,
 CURRENT_CONFORMING_CNT,
 CURRENT_MITIGATED_CNT,
 FINAL_UNACCPT_CNT,
 FINAL_DEFICIENT_CNT,
 FINAL_CONFORMING_CNT,
 FINAL_MITIGATED_CNT,
 INDEM_CNT,
 CLOSED_CNT,
 PURCHASE_CNT,
 --STREAMLINE_REFI_CNT,  need more info
 --RATE_and_TERM_REFI_CNT, need more info
 HECM_CNT,
 FINAL_REMEDIATED_CNT,
 CURRENT_REMEDIATED_CNT,
 SELECTION_CLASS,--alter table REVIEW_MATRIX alter column SELECTION_CLASS varchar(50) null
 CAL_YEAR,
 CAL_QTR,
 RVWLVL_INITIAL_CNT,
 RVWLVL_MITIGATION_1_CNT,
 RVWLVL_MITIGATION_2_CNT,
 RVWLVL_MITIGATION_3_CNT,
 RVWLVL_MITIGATION_4_CNT,
 RVWLVL_MITIGATION_5_CNT,
 RVWLVL_ESCALATION_1_CNT,
 RVWLVL_ESCALATION_2_CNT,
 RVWLVL_ESCALATION_3_CNT,
 RVWLVL_ESCALATION_4_CNT,
 RVWLVL_HQ_ESCALATION_1_CNT,
 RVWLVL_HQ_ESCALATION_2_CNT,
 RVWLVL_HQ_ESCALATION_3_CNT,
 RVWLVL_HQ_ESCALATION_4_CNT,
 CURRENT_STATUS,
 STATUS_DISPLAY_ORDER,
 GEOLOC,
 HOC,
 REVIEW_TYPE,
 DAYS_IN_CURRENT_ITERATION,
 DAYS_IN_TOTAL_REVIEW,
 RVWR_PRSNNL_ID,
 REPORTS_TO_PRSNNL_ID)
 select
 REVIEW.REVIEW_REFERENCE_ID,
 REVIEW.CASE_NUMBER,
 (select DATEFROMPARTS(YEAR(LOAN_SELECTION.SELECTION_DT),MONTH(LOAN_SELECTION.SELECTION_DT),1)
  from LOAN_SELECTION where LOAN_SELECTION.SELECTION_ID = REVIEW.SELECTION_ID) as SELECTION_DATE,
-- 
 (select CASE WHEN DATEPART(MONTH,SELECTION_DT) < 10 THEN DATEPART(YEAR,SELECTION_DT)
			        else DATEPART(YEAR,SELECTION_DT) + 1 end
  from LOAN_SELECTION where LOAN_SELECTION.SELECTION_ID = REVIEW.SELECTION_ID) as FISCAL_YEAR,
-- 
 (select CASE WHEN DATEPART(MONTH,SELECTION_DT) in (10,11,12) THEN CONVERT(char(4), DATEPART(YEAR,SELECTION_DT)+ 1)  + ' ' + 'Q1'
              WHEN DATEPART(MONTH,SELECTION_DT) in (1,2,3) THEN CONVERT(char(4), DATEPART(YEAR,SELECTION_DT)) + ' ' + 'Q2'
              WHEN DATEPART(MONTH,SELECTION_DT) in (4,5,6) THEN CONVERT(char(4), DATEPART(YEAR,SELECTION_DT)) + ' ' + 'Q3'
              else CONVERT(char(4), DATEPART(YEAR,SELECTION_DT)) + ' ' + 'Q4' end
  from LOAN_SELECTION where LOAN_SELECTION.SELECTION_ID = REVIEW.SELECTION_ID) as FISCAL_QTR,
-- 
 (select MTGEE5 from LOAN_SELECTION where LOAN_SELECTION.SELECTION_ID = REVIEW.SELECTION_ID) as MTGEE5,
--
 COALESCE((select 1 from REVIEW_LEVEL a,
                         REVIEW_LEVEL_TYPE_REF b,
                         RATING_REF c
           where a.REVIEW_LEVEL_TYPE_ID = b.REVIEW_LEVEL_TYPE_ID
             and b.REVIEW_LEVEL_CD = 'INIT'
             and a.RATING_ID = c.RATING_ID
             and c.CODE = 'U'
             and a.REVIEW_ID = REVIEW.REVIEW_ID),0) as INITIAL_UNACCPT_CNT,
--            
 COALESCE((select 1 from REVIEW_LEVEL a,
                         REVIEW_LEVEL_TYPE_REF b,
                         RATING_REF c
           where a.REVIEW_LEVEL_TYPE_ID = b.REVIEW_LEVEL_TYPE_ID
             and b.REVIEW_LEVEL_CD = 'INIT'
             and a.RATING_ID = c.RATING_ID
             and c.CODE = 'D'
             and a.REVIEW_ID = REVIEW.REVIEW_ID),0) as INITIAL_DEFICIENT_CNT,
--             
 COALESCE((select 1 from REVIEW_LEVEL a,
                         REVIEW_LEVEL_TYPE_REF b,
                         RATING_REF c
           where a.REVIEW_LEVEL_TYPE_ID = b.REVIEW_LEVEL_TYPE_ID
             and b.REVIEW_LEVEL_CD = 'INIT'
             and a.RATING_ID = c.RATING_ID
             and c.CODE = 'C'
             and a.REVIEW_ID = REVIEW.REVIEW_ID),0) as INITIAL_CONFORMING_CNT,
--             
COALESCE((select 1 from (select b.CODE,
                                row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
                     	   from REVIEW_LEVEL a,
                     	        RATING_REF b
                     	   where a.RATING_ID = b.RATING_ID
                     		   and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and CODE = 'U'),0) as CURRENT_UNACCPT_CNT,
--            
COALESCE((select 1 from (select b.CODE,
                                row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
	                       from REVIEW_LEVEL a,
	                            RATING_REF b
                         where a.RATING_ID = b.RATING_ID
                           and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and CODE = 'D'),0) as CURRENT_DEFICIENT_CNT,
--            
COALESCE((select 1 from (select b.CODE,
                                row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
                         from REVIEW_LEVEL a,
                              RATING_REF b
                         where a.RATING_ID = b.RATING_ID
                           and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and CODE = 'C'),0) as CURRENT_CONFORMING_CNT,
--            
COALESCE((select 1 from (select b.CODE,
                                row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
                         from REVIEW_LEVEL a,
                              RATING_REF b
                         where a.RATING_ID = b.RATING_ID
                           and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and CODE = 'M'),0) as CURRENT_MITIGATED_CNT,
--            
COALESCE((select 1 from (select b.CODE,
                                row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
                         from REVIEW_LEVEL a,
                              RATING_REF b
		                     where a.RATING_ID = b.RATING_ID
                           and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and CODE = 'U'),0) as FINAL_UNACCPT_CNT,
--            
COALESCE((select 1 from (select b.CODE,
                                row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
                         from REVIEW_LEVEL a,
                              RATING_REF b
                      	 where a.RATING_ID = b.RATING_ID
                       	   and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and CODE = 'D'),0) as FINAL_DEFICIENT_CNT,
--            
COALESCE((select 1 from (select b.CODE,
                                row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
                    	   from REVIEW_LEVEL a,
                    	        RATING_REF b
                         where a.RATING_ID = b.RATING_ID
                           and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and CODE = 'C'),0) as FINAL_CONFORMING_CNT,
--            
COALESCE((select 1 from (select b.CODE,
                                row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
	                       from REVIEW_LEVEL a,
	                            RATING_REF b
		                     where a.RATING_ID = b.RATING_ID
		                       and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and CODE = 'M'),0) as FINAL_MITIGATED_CNT,
--            
COALESCE((select 1 from (select INDEM_DATE,
	                              row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
	                      from REVIEW_LEVEL a
		                    where a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and INDEM_DATE IS NOT NULL),0) as INDEM_CNT,
--            
CASE WHEN RVW_COMPLTD_DT is not null then 1
     else 0 end as CLOSED_CNT,
--     
COALESCE((select 1 
          from LOAN_SELECTION a,
		           PRODUCT_TYPE_REF b
		      where a.SELECTION_ID = REVIEW.SELECTION_ID
		        and a.PRODUCT_TYPE_ID = b.PRODUCT_TYPE_ID
			      and b.PRODUCT_TYPE_CD = 'PRCHS'),0) as PURCHASE_CNT,
--			      
COALESCE((select 1 
          from LOAN_SELECTION a,
               PRODUCT_TYPE_REF b
		      where a.SELECTION_ID = REVIEW.SELECTION_ID
		        and a.PRODUCT_TYPE_ID = b.PRODUCT_TYPE_ID
			      and b.PRODUCT_TYPE_CD = 'HECM '),0) as HECM_CNT,
--			      
COALESCE((select 1
          from (select b.CODE,
                       row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
	              from REVIEW_LEVEL a,
	                   RATING_REF b
		            where a.RATING_ID = b.RATING_ID
		              and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and CODE = 'R'),0) as FINAL_REMEDIATED_CNT,
--            
COALESCE((select 1
          from (select b.CODE,
                       row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
               from REVIEW_LEVEL a,
                    RATING_REF b
              where a.RATING_ID = b.RATING_ID
                and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and CODE = 'R'),0) as CURRENT_REMEDIATED_CNT,
--
(select b.DESCRIPTION
 from LOAN_SELECTION a,
      SELECTION_REASON b
 where a.SELECTION_ID = REVIEW.SELECTION_ID
   and a.SELECTION_REASON_ID = b.SELECTION_REASON_ID) as SELECTION_CLASS ,
--   
( select DATEPART(YEAR,SELECTION_DT)
  from LOAN_SELECTION where LOAN_SELECTION.SELECTION_ID = REVIEW.SELECTION_ID) as CAL_YEAR,
--  
(select CASE WHEN DATEPART(MONTH,SELECTION_DT) in (1,2,3) THEN  CONVERT(char(4), DATEPART(YEAR,SELECTION_DT)) + ' ' + 'Q1'
             WHEN DATEPART(MONTH,SELECTION_DT) in (4,5,6) THEN CONVERT(char(4), DATEPART(YEAR,SELECTION_DT)) + ' ' + 'Q2'
             WHEN DATEPART(MONTH,SELECTION_DT) in (7,8,9) THEN CONVERT(char(4), DATEPART(YEAR,SELECTION_DT)) + ' ' + 'Q3'
             else CONVERT(char(4), DATEPART(YEAR,SELECTION_DT)) + ' ' + 'Q4' end
 from LOAN_SELECTION where LOAN_SELECTION.SELECTION_ID = REVIEW.SELECTION_ID) as CAL_QTR,
-- 
COALESCE((select 1 from (select b.REVIEW_LEVEL_CD,
                                row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
	                       from REVIEW_LEVEL a,
	                            REVIEW_LEVEL_TYPE_REF b
		                     where a.REVIEW_LEVEL_TYPE_ID = b.REVIEW_LEVEL_TYPE_ID
		                       and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
           and REVIEW_LEVEL_CD = 'INIT'),0) as RVWLVL_INITIAL_CNT,
--           
COALESCE((select 1 from (select b.REVIEW_LEVEL_CD,
                                ITERATION_NUMBER,
	                              row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
	                       from REVIEW_LEVEL a,
	                            REVIEW_LEVEL_TYPE_REF b
                         where a.REVIEW_LEVEL_TYPE_ID = b.REVIEW_LEVEL_TYPE_ID
                           and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and REVIEW_LEVEL_CD = 'MITG'
            and ITERATION_NUMBER = 1),0) as RVWLVL_MITIGATION_1_CNT,
--            
COALESCE((select 1 from (select b.REVIEW_LEVEL_CD,
                                ITERATION_NUMBER,
	                              row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
	                      from REVIEW_LEVEL a,
	                           REVIEW_LEVEL_TYPE_REF b
                        where a.REVIEW_LEVEL_TYPE_ID = b.REVIEW_LEVEL_TYPE_ID
                          and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and REVIEW_LEVEL_CD = 'MITG'
            and ITERATION_NUMBER = 2),0) as RVWLVL_MITIGATION_2_CNT,
--            
COALESCE((select 1 from (select b.REVIEW_LEVEL_CD,
                                ITERATION_NUMBER,
	                              row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
	                       from REVIEW_LEVEL a,
	                            REVIEW_LEVEL_TYPE_REF b
                         where a.REVIEW_LEVEL_TYPE_ID = b.REVIEW_LEVEL_TYPE_ID
                           and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and REVIEW_LEVEL_CD = 'MITG'
            and ITERATION_NUMBER = 3),0) as RVWLVL_MITIGATION_3_CNT,
--            
COALESCE((select 1 from (select b.REVIEW_LEVEL_CD,
                                ITERATION_NUMBER,
	                              row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
                         from REVIEW_LEVEL a,
                              REVIEW_LEVEL_TYPE_REF b
                         where a.REVIEW_LEVEL_TYPE_ID = b.REVIEW_LEVEL_TYPE_ID
                           and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and REVIEW_LEVEL_CD = 'MITG'
            and ITERATION_NUMBER = 4),0) as RVWLVL_MITIGATION_4_CNT,
COALESCE((select 1 from (select b.REVIEW_LEVEL_CD,
                                ITERATION_NUMBER,
                                row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
                         from REVIEW_LEVEL a,
                              REVIEW_LEVEL_TYPE_REF b
                         where a.REVIEW_LEVEL_TYPE_ID = b.REVIEW_LEVEL_TYPE_ID
                           and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and REVIEW_LEVEL_CD = 'MITG'
            and ITERATION_NUMBER = 5),0) as RVWLVL_MITIGATION_5_CNT,
--
COALESCE((select 1 from (select b.REVIEW_LEVEL_CD,
                                ITERATION_NUMBER,
                                row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
                        from REVIEW_LEVEL a,
                             REVIEW_LEVEL_TYPE_REF b
                        where a.REVIEW_LEVEL_TYPE_ID = b.REVIEW_LEVEL_TYPE_ID
                          and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and REVIEW_LEVEL_CD = 'ESCL'
            and ITERATION_NUMBER = 1),0) as RVWLVL_ESCALATION_1_CNT,
--
COALESCE((select 1 from (select b.REVIEW_LEVEL_CD,
                                ITERATION_NUMBER,
                                row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
                         from REVIEW_LEVEL a,
                              REVIEW_LEVEL_TYPE_REF b
                         where a.REVIEW_LEVEL_TYPE_ID = b.REVIEW_LEVEL_TYPE_ID
                           and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and REVIEW_LEVEL_CD = 'ESCL'
            and ITERATION_NUMBER = 2),0) as RVWLVL_ESCALATION_2_CNT,
--            
COALESCE((select 1 from (select b.REVIEW_LEVEL_CD,
                                ITERATION_NUMBER,
                                row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
                         from REVIEW_LEVEL a,
                              REVIEW_LEVEL_TYPE_REF b
                         where a.REVIEW_LEVEL_TYPE_ID = b.REVIEW_LEVEL_TYPE_ID
                           and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and REVIEW_LEVEL_CD = 'ESCL'
            and ITERATION_NUMBER = 3),0) as RVWLVL_ESCALATION_3_CNT,
--            
COALESCE((select 1 from (select b.REVIEW_LEVEL_CD,
                                ITERATION_NUMBER,
	                              row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
                         from REVIEW_LEVEL a,
                              REVIEW_LEVEL_TYPE_REF b
                         where a.REVIEW_LEVEL_TYPE_ID = b.REVIEW_LEVEL_TYPE_ID
                           and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and REVIEW_LEVEL_CD = 'ESCL'
            and ITERATION_NUMBER = 4),0) as RVWLVL_ESCALATION_4_CNT,
--
COALESCE((select 1 from (select b.REVIEW_LEVEL_CD,
                                ITERATION_NUMBER,
	                              row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
	                       from REVIEW_LEVEL a,
                              REVIEW_LEVEL_TYPE_REF b
		                     where a.REVIEW_LEVEL_TYPE_ID = b.REVIEW_LEVEL_TYPE_ID
		                       and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and REVIEW_LEVEL_CD = 'HQES'
            and ITERATION_NUMBER = 1),0) as RVWLVL_HQ_ESCALATION_1_CNT,
--            
COALESCE((select 1 from (select b.REVIEW_LEVEL_CD,
                                ITERATION_NUMBER,
	                              row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
	                       from REVIEW_LEVEL a,
	                            REVIEW_LEVEL_TYPE_REF b
		                     where a.REVIEW_LEVEL_TYPE_ID = b.REVIEW_LEVEL_TYPE_ID
		                       and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
           where RN = 1
             and REVIEW_LEVEL_CD = 'HQES'
             and ITERATION_NUMBER = 2),0) as RVWLVL_HQ_ESCALATION_2_CNT,
--             
COALESCE((select 1 from (select b.REVIEW_LEVEL_CD,
                                ITERATION_NUMBER,
	                              row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
	                       from REVIEW_LEVEL a,
                              REVIEW_LEVEL_TYPE_REF b
		                     where a.REVIEW_LEVEL_TYPE_ID = b.REVIEW_LEVEL_TYPE_ID
		                       and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and REVIEW_LEVEL_CD = 'HQES'
            and ITERATION_NUMBER = 3),0) as RVWLVL_HQ_ESCALATION_3_CNT,
--            
COALESCE((select 1 from (select b.REVIEW_LEVEL_CD,
                                ITERATION_NUMBER,
	                              row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
                         from REVIEW_LEVEL a,
                              REVIEW_LEVEL_TYPE_REF b
                         where a.REVIEW_LEVEL_TYPE_ID = b.REVIEW_LEVEL_TYPE_ID
                       	   and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
          where RN = 1
            and REVIEW_LEVEL_CD = 'HQES'
            and ITERATION_NUMBER = 4),0) as RVWLVL_HQ_ESCALATION_4_CNT,
--            
(select CASE WHEN REVIEW_LEVEL_CD = 'INIT' THEN DESCRIPTION
             else DESCRIPTION + ' ' + CONVERT(char(1), ITERATION_NUMBER) end
 from (select c.DESCRIPTION,
              ITERATION_NUMBER,
	            c.REVIEW_LEVEL_CD,
	            row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
	     from REVIEW_LEVEL a,
	          REVIEW_LEVEL_STATUS_REF b,
			      REVIEW_LEVEL_TYPE_REF c
     	where a.REVIEW_LEVEL_STATUS_ID = b.REVIEW_LEVEL_STATUS_ID
		    and a.REVIEW_LEVEL_TYPE_ID = c.REVIEW_LEVEL_TYPE_ID
		    and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
 where RN = 1) as CURRENT_STATUS,
-- 
(select CASE WHEN REVIEW_LEVEL_CD = 'INIT' then '10'
             WHEN REVIEW_LEVEL_CD = 'MITG' then '2' + CONVERT(char(1), ITERATION_NUMBER)
             WHEN REVIEW_LEVEL_CD = 'ESCL' then '3' + CONVERT(char(1), ITERATION_NUMBER)
             WHEN REVIEW_LEVEL_CD = 'HQES' then '4' + CONVERT(char(1), ITERATION_NUMBER)
             WHEN REVIEW_LEVEL_CD = 'INDM' then '5' + CONVERT(char(1), ITERATION_NUMBER)
	      else '99' end
 from (select b.REVIEW_LEVEL_CD,
              ITERATION_NUMBER,
	            row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
	     from REVIEW_LEVEL a,
	          REVIEW_LEVEL_TYPE_REF b
		   where a.REVIEW_LEVEL_TYPE_ID = b.REVIEW_LEVEL_TYPE_ID
		     and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
 where RN = 1) as STATUS_DISPLAY_ORDER,
-- 
(select LOCATION_NAME
 from (select b.LOCATION_NAME,
	            row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
	     from REVIEW_LEVEL a,
	          REVIEW_LOCATION b
		   where a.REVIEW_LOCATION_ID = b.REVIEW_LOCATION_ID
		     and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
 where RN = 1) as GEOLOC,
-- 
(select SUBSTRING(LOCATION_NAME, 1,3)
 from (select b.LOCATION_NAME,
	            row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
	     from REVIEW_LEVEL a,
	          REVIEW_LOCATION b
		   where a.REVIEW_LOCATION_ID = b.REVIEW_LOCATION_ID
		     and a.REVIEW_ID = REVIEW.REVIEW_ID) as T
 where RN = 1) as HOC,
-- 
(select DESCRIPTION
 from LOAN_SELECTION a,
      REVIEW_TYPE_REF b
 where a.SELECTION_ID = REVIEW.SELECTION_ID
   and a.REVIEW_TYPE_ID = b.REVIEW_TYPE_ID) as REVIEW_TYPE,
--   
(select CASE WHEN COMPLT_DT is null then DATEDIFF(day, ASSIGN_DT,getdate())
             else DATEDIFF(day, ASSIGN_DT,COMPLT_DT) end
 from (select ASSIGN_DT,
              COMPLT_DT,
	            row_number() over(partition by REVIEW_ID order by ASSIGN_DT desc) as RN
	     from REVIEW_LEVEL a
		   where a.REVIEW_ID = REVIEW.REVIEW_ID) as T
 where RN = 1) as DAYS_IN_CURRENT_ITERATION,
-- 
 CASE WHEN RVW_COMPLTD_DT is null then DATEDIFF(day, RVW_STARTED_DT_TM,getdate())
      else DATEDIFF(day, RVW_STARTED_DT_TM,RVW_COMPLTD_DT) end as DAYS_IN_TOTAL_REVIEW,
--      
 null,
 null
 from REVIEW 

 update A
set A.STATIC_GRS_MTRL_DFCT_PF = B.static_gross_pct,
    A.STATIC_NET_MTRL_DFCT_PF = B.static_net_pct
from review_matrix A,
(select  sum(closed_cnt) static_review_cnt, sum(final_remediated_cnt) + sum(FINAL_UNACCPT_CNT) as static_gross,
       (0.0 + sum(final_remediated_cnt) + sum(FINAL_UNACCPT_CNT)) / sum(closed_cnt) as static_gross_pct,
       sum(final_remediated_cnt) + sum(FINAL_UNACCPT_CNT) + sum(FINAL_MITIGATED_CNT) as static_net,
       (0.0 + sum(final_remediated_cnt) + sum(FINAL_UNACCPT_CNT) + sum(FINAL_MITIGATED_CNT)) / sum(closed_cnt) as static_net_pct
       from review_matrix where closed_cnt = 1 
       and selection_dt > dateadd(MM,-24,current_timestamp)
       and SELECTION_CLASS in ('Early Claims', 'Early Payment Default', 'FHA Manual', 
       'Lender Increased', 'Lender Monitoring', 'Risk Monthly (Threshold)', 'Underwriter Increased')) B
where A.selection_dt > dateadd(MM,-24,current_timestamp)
       and A.SELECTION_CLASS in ('Early Claims', 'Early Payment Default', 'FHA Manual', 
       'Lender Increased', 'Lender Monitoring', 'Risk Monthly (Threshold)', 'Underwriter Increased');
;

update A
set A.STATIC_GRS_MTRL_DFCT_NPF = B.static_gross_pct,
    A.STATIC_NET_MTRL_DFCT_NPF = B.static_net_pct
from review_matrix A,
(select  sum(closed_cnt) static_review_cnt, sum(final_remediated_cnt) + sum(FINAL_UNACCPT_CNT) as static_gross,
       (0.0 + sum(final_remediated_cnt) + sum(FINAL_UNACCPT_CNT)) / sum(closed_cnt) as static_gross_pct,
       sum(final_remediated_cnt) + sum(FINAL_UNACCPT_CNT) + sum(FINAL_MITIGATED_CNT) as static_net,
       (0.0 + sum(final_remediated_cnt) + sum(FINAL_UNACCPT_CNT) + sum(FINAL_MITIGATED_CNT)) / sum(closed_cnt) as static_net_pct
       from review_matrix where closed_cnt = 1 
       and selection_dt > dateadd(MM,-24,current_timestamp)
       and SELECTION_CLASS in ('Random Monthly', 'Test Case')) B
where A.selection_dt > dateadd(MM,-24,current_timestamp)
       and A.SELECTION_CLASS in ('Random Monthly', 'Test Case');

update A
set A.STATIC_GRS_MTRL_DFCT = B.static_gross_pct,
    A.STATIC_NET_MTRL_DFCT = B.static_net_pct
from review_matrix A,
(select  sum(closed_cnt) static_review_cnt, sum(final_remediated_cnt) + sum(FINAL_UNACCPT_CNT) as static_gross,
       (0.0 + sum(final_remediated_cnt) + sum(FINAL_UNACCPT_CNT)) / sum(closed_cnt) as static_gross_pct,
       sum(final_remediated_cnt) + sum(FINAL_UNACCPT_CNT) + sum(FINAL_MITIGATED_CNT) as static_net,
       (0.0 + sum(final_remediated_cnt) + sum(FINAL_UNACCPT_CNT) + sum(FINAL_MITIGATED_CNT)) / sum(closed_cnt) as static_net_pct
       from review_matrix where closed_cnt = 1 
       and selection_dt > dateadd(MM,-24,current_timestamp)
       and SELECTION_CLASS in ('Random Monthly', 'Early Claims', 'Early Payment Default', 'FHA Manual', 'Lender Increased', 'Lender Monitoring',
       'Lender Self Report', 'National QC', 'Risk Monthly (Threshold)', 'Test Case', 'Underwriter Increased')) B
where A.selection_dt > dateadd(MM,-24,current_timestamp)
       and A.SELECTION_CLASS in ('Random Monthly', 'Early Claims', 'Early Payment Default', 'FHA Manual', 'Lender Increased', 'Lender Monitoring',
       'Lender Self Report', 'National QC', 'Risk Monthly (Threshold)', 'Test Case', 'Underwriter Increased');


