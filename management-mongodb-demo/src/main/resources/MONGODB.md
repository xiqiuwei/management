db.mydb1.find({"$and":[{"indusId":300},{"areaCode":310115}]}).sort({"date":-1,"value":-1}).limit(31)
这是一句mongo的查询加排序以及限定数量语句，下面就来用java写这句查询并且介绍下他其中的api以及类的用法
Criteria类：它封装所有的语句，以方法的形式进行查询
eg: 1.Criteria.where("操作的字段名").is("值").and("操作的字段名").is("值");
      还有种多条件查询就是Criteria.where("操作的字段名").is("值")
                                  .andOperator(Criteria.where("操作的字段名").is("值"));
    2.Criteria.andOperator(Criteria.where("操作的字段名").is("值"),Criteria.where("操作的字段名").in("值"))
Query类：这是将语句进行封装或者添加排序之类的操作
eg: 1.接上上面的Criteria类后Query的用法Query query = new Query(criteria);
                                       query.with(new Sort(new Order(Direction.DESC,"根据什么字段进行降序排"),
                                       new Order(Direction.DESC,"根据什么字段降序排")));
                                       query.limit(5); // 截取5条数据
BasicDBObject类：根据字段查询指定字段