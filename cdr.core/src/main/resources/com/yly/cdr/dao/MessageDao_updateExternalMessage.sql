UPDATE MESSAGE a
     SET a.external_state = /*externalState*/'01',
     a.update_time=sysdate
   WHERE a.id=/*id*/'1'
   and a.create_time=to_date(/*createTime*/'2012-10-10', 'YYYY-MM-DD hh24:mi:ss')
   
   