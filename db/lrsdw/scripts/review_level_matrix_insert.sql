create proc sp_load_review_level_matrix
as

truncate table REVIEW_LEVEL_MATRIX

insert into REVIEW_LEVEL_MATRIX
(REVIEW_LVL_ID,
 ASSIGN_DT,
 COMPLT_DT,
 INDEM_DATE,
 INDEM_TYPE,
 ITERATION_NUM,
 REVIEW_REFERENCE_ID,
 REVIEW_LEVEL_NAME,
 REVIEW_LEVEL_STATUS,
 RVW_LOCATION_NAME,
 RVW_STARTED_DT,
 RVWR_PRSNNL_ID,
 REVIEW_LEVEL_TOTAL_DAYS,
 REVIEW_LEVEL_CALC_DAYS,
 OUTCOME_ESCALATION_CNT,
 OUTCOME_GRS_MAT_DEFECT_CNT,
 OUTCOME_NET_MAT_DEFECT_CNT,
 REVIEW_LEVEL_CNT,
 CASE_NUMBER,
 MTGEE5,
 REVIEW_TYPE,
 compl_inside_target_cnt,
 compl_outside_target_cnt,
 compl_inside_target_mit_cnt,
 compl_outside_target_mit_cnt,
 compl_inside_target_esc_cnt,
 compl_outside_target_esc_cnt,
 REVIEW_LEVEL_INITIAL_CNT,
 REVIEW_LEVEL_MITIGATED_CNT,
 ASSIGN_MONTH,
 REPORTS_TO_PRSNNL_ID,
 REVIEW_LEVEL_ESCALATED_CNT,
 LEVEL_INSIDE_TARGET_CNT,
 LEVEL_OUTSIDE_TARGET_CNT,
 STATUS_DISPLAY_ORDER,
 REVIEW_TOTAL_DAYS,
 location_order,
 SELECTION_REASON,
 REVIEW_LEVEL_INITIAL_DAYS,
 REVIEW_LEVEL_MITIGATION_DAYS,
 REVIEW_LEVEL_ESCALATION_DAYS,
 REVIEW_LEVEL_HQ_ESCL_DAYS,
 OUTCOME_CONFORMING_CNT,
 OUTCOME_DEFICIENT_CNT,
 OUTCOME_MITIGATED_CNT,
 OUTCOME_REMEDIATED_CNT,
 OUTCOME_INDEM_CNT,
 OUTCOME_OTHER_CNT)
select 
 REVIEW_LEVEL_ID,
 ASSIGN_DT,
 COMPLT_DT,
 INDEM_DATE,
 (select INDEMNIFICATION_TYPE_DESCRIPTION
  from INDEMNIFICATION_TYPE_REF a
  WHERE a.INDEMNIFICATION_TYPE_ID = REVIEW_LEVEL.INDEMNIFICATION_TYPE_ID) as INDEM_TYPE,
 ITERATION_NUMBER,
 (select REVIEW_REFERENCE_ID
  from REVIEW a
  where a.REVIEW_ID = REVIEW_LEVEL.REVIEW_ID),
 (select DESCRIPTION + ' ' + CONVERT(CHAR(1), ITERATION_NUMBER)
  from REVIEW_LEVEL_TYPE_REF a
  where A.REVIEW_LEVEL_TYPE_ID = REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID) as  REVIEW_LEVEL_NAME,
 (select DESCRIPTION
  from REVIEW_LEVEL_STATUS_REF a
  where a.REVIEW_LEVEL_STATUS_ID = REVIEW_LEVEL.REVIEW_LEVEL_STATUS_ID) as REVIEW_LEVEL_STATUS,
 (select LOCATION_NAME
  from REVIEW_LOCATION a
  where a.REVIEW_LOCATION_ID = REVIEW_LEVEL.REVIEW_LOCATION_ID) as RVW_LOCATION_NAME,
 (select a.RVW_STARTED_DT_TM
  from REVIEW a
  where a.REVIEW_ID = REVIEW_LEVEL.REVIEW_ID) as RVW_STARTED_DT,
  REVIEWER_PERSONNEL_ID,
  CASE WHEN COMPLT_DT is null then DATEDIFF(day, ASSIGN_DT,getdate())
             else DATEDIFF(day, ASSIGN_DT,COMPLT_DT) end as REVIEW_LEVEL_TOTAL_DAYS,
  CASE WHEN COMPLT_DT is null then DATEDIFF(day, ASSIGN_DT,getdate())
             else DATEDIFF(day, ASSIGN_DT,COMPLT_DT) end as REVIEW_LEVEL_CALC_DAYS,
  (select CASE WHEN CODE = 'U' THEN 1
               else 0 end
   from RATING_REF a
   where a.RATING_ID = REVIEW_LEVEL.RATING_ID) as OUTCOME_ESCALATION_CNT,
  (select CASE WHEN CODE in ('R','U') THEN 1
               else 0 end
   from RATING_REF a
   where a.RATING_ID = REVIEW_LEVEL.RATING_ID) as OUTCOME_GRS_MAT_DEFECT_CNT,
  (select CASE WHEN CODE in ('R','M','U') THEN 1
               else 0 end
   from RATING_REF a
   where a.RATING_ID = REVIEW_LEVEL.RATING_ID) as OUTCOME_NET_MAT_DEFECT_CNT,
   1 as REVIEW_LEVEL_CNT,
  (select CASE_NUMBER
   from REVIEW a
   where a.REVIEW_ID = REVIEW_LEVEL.REVIEW_ID) as CASE_NUMBER,
  (select MTGEE5
   from LOAN_SELECTION a,
        REVIEW b
   where a.SELECTION_ID = b.SELECTION_ID
     and b.REVIEW_ID = REVIEW_LEVEL.REVIEW_ID) as MTGEE5,
  (select c.DESCRIPTION
   from LOAN_SELECTION a,
        REVIEW b,
		REVIEW_TYPE_REF c
   where a.SELECTION_ID = b.SELECTION_ID
     and b.REVIEW_ID = REVIEW_LEVEL.REVIEW_ID
	 and a.REVIEW_TYPE_ID = c.REVIEW_TYPE_ID) as REVIEW_TYPE,
  COALESCE((select 
            case when REVIEW_DAYS_ITERATION_1 >
                   (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                         else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
            		  else 0 end
            from REVIEW_LEVEL_ITERATION_TIMEFRAME a,
            	 REVIEW_LEVEL_TYPE_REF c,
            	 REVIEW d,
            	 LOAN_SELECTION e
            where REVIEW_LEVEL.REVIEW_ID = d.REVIEW_ID
              and REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = c.REVIEW_LEVEL_TYPE_ID
              and c.REVIEW_LEVEL_CD = 'INIT'
              and REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = a.REVIEW_LEVEL_TYPE_ID
              and d.SELECTION_ID = e.SELECTION_ID
              and e.SELECTION_REASON_ID = a.SELECTION_REASON_ID
   ),0) as compl_inside_target_cnt,
  COALESCE((select 
            case when REVIEW_DAYS_ITERATION_1 <
                   (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                         else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
            		  else 0 end
            from REVIEW_LEVEL_ITERATION_TIMEFRAME a,
            	 REVIEW_LEVEL_TYPE_REF c,
            	 REVIEW d,
            	 LOAN_SELECTION e
            where REVIEW_LEVEL.REVIEW_ID = d.REVIEW_ID
              and REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = c.REVIEW_LEVEL_TYPE_ID
              and c.REVIEW_LEVEL_CD = 'INIT'
              and REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = a.REVIEW_LEVEL_TYPE_ID
              and d.SELECTION_ID = e.SELECTION_ID
              and e.SELECTION_REASON_ID = a.SELECTION_REASON_ID
   ),0) as compl_outside_target_cnt,
  COALESCE((select 
            case when REVIEW_LEVEL.ITERATION_NUMBER = 1 then
                   (case when REVIEW_DAYS_ITERATION_1 >
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 2 then
                   (case when REVIEW_DAYS_ITERATION_2 >
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 3 then
                   (case when REVIEW_DAYS_ITERATION_3 >
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 4 then
                   (case when REVIEW_DAYS_ITERATION_4 >
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 5 then
                   (case when REVIEW_DAYS_ITERATION_5 >
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 end
            from REVIEW_LEVEL_ITERATION_TIMEFRAME a,
            	 REVIEW_LEVEL_TYPE_REF c,
            	 REVIEW d,
            	 LOAN_SELECTION e
            where REVIEW_LEVEL.REVIEW_ID = d.REVIEW_ID
              and REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = c.REVIEW_LEVEL_TYPE_ID
              and c.REVIEW_LEVEL_CD = 'MITG'
              and REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = a.REVIEW_LEVEL_TYPE_ID
              and d.SELECTION_ID = e.SELECTION_ID
              and e.SELECTION_REASON_ID = a.SELECTION_REASON_ID
   ),0) as compl_inside_target_mit_cnt,
  COALESCE((select 
            case when REVIEW_LEVEL.ITERATION_NUMBER = 1 then
                   (case when REVIEW_DAYS_ITERATION_1 <
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 2 then
                   (case when REVIEW_DAYS_ITERATION_2 <
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 3 then
                   (case when REVIEW_DAYS_ITERATION_3 <
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 4 then
                   (case when REVIEW_DAYS_ITERATION_4 <
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 5 then
                   (case when REVIEW_DAYS_ITERATION_5 <
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 end
            from REVIEW_LEVEL_ITERATION_TIMEFRAME a,
            	 REVIEW_LEVEL_TYPE_REF c,
            	 REVIEW d,
            	 LOAN_SELECTION e
            where REVIEW_LEVEL.REVIEW_ID = d.REVIEW_ID
              and REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = c.REVIEW_LEVEL_TYPE_ID
              and c.REVIEW_LEVEL_CD = 'MITG'
              and REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = a.REVIEW_LEVEL_TYPE_ID
              and d.SELECTION_ID = e.SELECTION_ID
              and e.SELECTION_REASON_ID = a.SELECTION_REASON_ID
   ),0) as compl_outside_target_mit_cnt,
--
  COALESCE((select 
            case when REVIEW_LEVEL.ITERATION_NUMBER = 1 then
                   (case when REVIEW_DAYS_ITERATION_1 >
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 2 then
                   (case when REVIEW_DAYS_ITERATION_2 >
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 3 then
                   (case when REVIEW_DAYS_ITERATION_3 >
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 4 then
                   (case when REVIEW_DAYS_ITERATION_4 >
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 5 then
                   (case when REVIEW_DAYS_ITERATION_5 >
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 end
            from REVIEW_LEVEL_ITERATION_TIMEFRAME a,
            	 REVIEW_LEVEL_TYPE_REF c,
            	 REVIEW d,
            	 LOAN_SELECTION e
            where REVIEW_LEVEL.REVIEW_ID = d.REVIEW_ID
              and REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = c.REVIEW_LEVEL_TYPE_ID
              and c.REVIEW_LEVEL_CD = 'ESCL'
              and REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = a.REVIEW_LEVEL_TYPE_ID
              and d.SELECTION_ID = e.SELECTION_ID
              and e.SELECTION_REASON_ID = a.SELECTION_REASON_ID
   ),0) as compl_inside_target_esc_cnt,
  COALESCE((select 
            case when REVIEW_LEVEL.ITERATION_NUMBER = 1 then
                   (case when REVIEW_DAYS_ITERATION_1 <
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 2 then
                   (case when REVIEW_DAYS_ITERATION_2 <
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 3 then
                   (case when REVIEW_DAYS_ITERATION_3 <
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 4 then
                   (case when REVIEW_DAYS_ITERATION_4 <
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 5 then
                   (case when REVIEW_DAYS_ITERATION_5 <
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 end
            from REVIEW_LEVEL_ITERATION_TIMEFRAME a,
            	 REVIEW_LEVEL_TYPE_REF c,
            	 REVIEW d,
            	 LOAN_SELECTION e
            where REVIEW_LEVEL.REVIEW_ID = d.REVIEW_ID
              and REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = c.REVIEW_LEVEL_TYPE_ID
              and c.REVIEW_LEVEL_CD = 'ESCL'
              and REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = a.REVIEW_LEVEL_TYPE_ID
              and d.SELECTION_ID = e.SELECTION_ID
              and e.SELECTION_REASON_ID = a.SELECTION_REASON_ID
   ),0) as compl_outside_target_esc_cnt,
   COALESCE((select 1 
            from REVIEW_LEVEL_TYPE_REF a
			where a.REVIEW_LEVEL_TYPE_ID = REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID
			  and a.REVIEW_LEVEL_CD = 'INIT'),0) as REVIEW_LEVEL_INITIAL_CNT ,
   COALESCE((select 1 
            from REVIEW_LEVEL_TYPE_REF a
			where a.REVIEW_LEVEL_TYPE_ID = REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID
			  and a.REVIEW_LEVEL_CD = 'MITG'),0) as REVIEW_LEVEL_MITIGATED_CNT,
   DATEFROMPARTS(YEAR(ASSIGN_DT),MONTH(ASSIGN_DT),1) as ASSIGN_MONTH,
   (select REPORTS_TO_PERSONNEL_ID
    from PERSONNEL a
	where a.PERSONNEL_ID = REVIEWER_PERSONNEL_ID) as REPORTS_TO_PERSONNEL_ID,
   COALESCE((select 1 
            from REVIEW_LEVEL_TYPE_REF a
			where a.REVIEW_LEVEL_TYPE_ID = REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID
			  and a.REVIEW_LEVEL_CD = 'ESCL'),0) as REVIEW_LEVEL_ESCALATED_CNT,
   (select 
            case when REVIEW_LEVEL.ITERATION_NUMBER = 1 then
                   (case when REVIEW_DAYS_ITERATION_1 >
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 2 then
                   (case when REVIEW_DAYS_ITERATION_2 >
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 3 then
                   (case when REVIEW_DAYS_ITERATION_3 >
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 4 then
                   (case when REVIEW_DAYS_ITERATION_4 >
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 5 then
                   (case when REVIEW_DAYS_ITERATION_5 >
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 end
            from REVIEW_LEVEL_ITERATION_TIMEFRAME a,
            	 REVIEW_LEVEL_TYPE_REF c,
            	 REVIEW d,
            	 LOAN_SELECTION e
            where REVIEW_LEVEL.REVIEW_ID = d.REVIEW_ID
              and REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = c.REVIEW_LEVEL_TYPE_ID
              and REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = a.REVIEW_LEVEL_TYPE_ID
              and d.SELECTION_ID = e.SELECTION_ID
              and e.SELECTION_REASON_ID = a.SELECTION_REASON_ID
   ) as LEVEL_INSIDE_TARGET_CNT,
   (select 
            case when REVIEW_LEVEL.ITERATION_NUMBER = 1 then
                   (case when REVIEW_DAYS_ITERATION_1 <
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 2 then
                   (case when REVIEW_DAYS_ITERATION_2 <
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 3 then
                   (case when REVIEW_DAYS_ITERATION_3 <
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 4 then
                   (case when REVIEW_DAYS_ITERATION_4 <
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 when REVIEW_LEVEL.ITERATION_NUMBER = 5 then
                   (case when REVIEW_DAYS_ITERATION_5 <
                          (case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                                else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end) then 1
                   		  else 0 end)
                 end
            from REVIEW_LEVEL_ITERATION_TIMEFRAME a,
            	 REVIEW_LEVEL_TYPE_REF c,
            	 REVIEW d,
            	 LOAN_SELECTION e
            where REVIEW_LEVEL.REVIEW_ID = d.REVIEW_ID
              and REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = c.REVIEW_LEVEL_TYPE_ID
              and REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = a.REVIEW_LEVEL_TYPE_ID
              and d.SELECTION_ID = e.SELECTION_ID
              and e.SELECTION_REASON_ID = a.SELECTION_REASON_ID
   ) as LEVEL_OUTSIDE_TARGET_CNT,
  (select CASE WHEN a.REVIEW_LEVEL_CD = 'INIT' then '10'
             WHEN a.REVIEW_LEVEL_CD = 'MITG' then '2' + CONVERT(char(1), ITERATION_NUMBER)
             WHEN a.REVIEW_LEVEL_CD = 'ESCL' then '3' + CONVERT(char(1), ITERATION_NUMBER)
             WHEN a.REVIEW_LEVEL_CD = 'HQES' then '4' + CONVERT(char(1), ITERATION_NUMBER)
             WHEN a.REVIEW_LEVEL_CD = 'INDM' then '5' + CONVERT(char(1), ITERATION_NUMBER)
	      else '99' end
	from REVIEW_LEVEL_TYPE_REF a
		   where REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = a.REVIEW_LEVEL_TYPE_ID) as STATUS_DISPLAY_ORDER,
  (select CASE WHEN RVW_COMPLTD_DT is null then DATEDIFF(day, RVW_STARTED_DT_TM,getdate())
               else DATEDIFF(day, RVW_STARTED_DT_TM,RVW_COMPLTD_DT) end 
   from REVIEW a
   where a.REVIEW_ID = REVIEW_LEVEL.REVIEW_ID) as REVIEW_TOTAL_DAYS,
 (select CASE WHEN LOCATION_NAME = 'ATL-PUD' then 100
              WHEN LOCATION_NAME = 'DEN-PUD' then 110
              WHEN LOCATION_NAME = 'PHI-PUD' then 120
              WHEN LOCATION_NAME = 'SNA-PUD' then 130
              WHEN LOCATION_NAME = 'ATL-QAD' then 200
              WHEN LOCATION_NAME = 'DEN-QAD' then 210
              WHEN LOCATION_NAME = 'PHI-QAD' then 220
              WHEN LOCATION_NAME = 'SNA-QAD' then 230
              WHEN LOCATION_NAME = 'HQ'      then 300 end
  from REVIEW_LOCATION a
  where a.REVIEW_LOCATION_ID = REVIEW_LEVEL.REVIEW_LOCATION_ID) as location_order,
(select b.DESCRIPTION
 from LOAN_SELECTION a,
      SELECTION_REASON b,
	  REVIEW c
 where a.SELECTION_REASON_ID = b.SELECTION_REASON_ID
   and c.SELECTION_ID = a.SELECTION_ID
   and c.REVIEW_ID = REVIEW_LEVEL.REVIEW_ID) as SELECTION_REASON,
--
 COALESCE((select case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                       else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end
            from REVIEW_LEVEL_TYPE_REF a
            where REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = a.REVIEW_LEVEL_TYPE_ID
              and a.REVIEW_LEVEL_CD = 'INIT'
          ),0) as REVIEW_LEVEL_INITIAL_DAYS,
 COALESCE((select case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                       else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end
            from REVIEW_LEVEL_TYPE_REF a
            where REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = a.REVIEW_LEVEL_TYPE_ID
              and a.REVIEW_LEVEL_CD = 'MITG'
          ),0) as REVIEW_LEVEL_MITIGATION_DAYS,
 COALESCE((select case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                       else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end
            from REVIEW_LEVEL_TYPE_REF a
            where REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = a.REVIEW_LEVEL_TYPE_ID
              and a.REVIEW_LEVEL_CD = 'ESCL'
          ),0) as REVIEW_LEVEL_ESCALATION_DAYS,
 COALESCE((select case when REVIEW_LEVEL.COMPLT_DT is null then DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,getdate())
                       else DATEDIFF(day, REVIEW_LEVEL.ASSIGN_DT,REVIEW_LEVEL.COMPLT_DT) end
            from REVIEW_LEVEL_TYPE_REF a
            where REVIEW_LEVEL.REVIEW_LEVEL_TYPE_ID = a.REVIEW_LEVEL_TYPE_ID
              and a.REVIEW_LEVEL_CD = 'HQES'
          ),0) as REVIEW_LEVEL_HQ_ESCL_DAYS,
  COALESCE((select 1 from 
   RATING_REF a
   where a.RATING_ID = REVIEW_LEVEL.RATING_ID
     and a.CODE = 'C'),0) as OUTCOME_CONFORMING_CNT,
  COALESCE((select 1 from 
   RATING_REF a
   where a.RATING_ID = REVIEW_LEVEL.RATING_ID
     and a.CODE = 'D'),0) as OUTCOME_DEFICIENT_CNT,
  COALESCE((select 1 from 
   RATING_REF a
   where a.RATING_ID = REVIEW_LEVEL.RATING_ID
     and a.CODE = 'M'),0) as OUTCOME_MITIGATED_CNT,
  COALESCE((select 1 from 
   RATING_REF a
   where a.RATING_ID = REVIEW_LEVEL.RATING_ID
     and a.CODE = 'R'),0) as OUTCOME_REMEDIATED_CNT,
  CASE WHEN INDEM_DATE is not null then 1
       else 0 end as OUTCOME_INDEM_CNT,
  COALESCE((select 1 from 
   RATING_REF a
   where a.RATING_ID = REVIEW_LEVEL.RATING_ID
     and a.CODE not in ('C','D','M','R')),0) as OUTCOME_OTHER_CNT
from REVIEW_LEVEL



         
