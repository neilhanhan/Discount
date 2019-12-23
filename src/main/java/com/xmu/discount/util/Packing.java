package com.xmu.discount.util;

/**
 * @author Liuwenhan
 */
public class Packing {

//    @Autowired
//    static GoodsService goodsService;

//    public static List<GrouponRuleVo> grouponRulePoPacking(List<GrouponRulePo> grouponRulePoList){
//        List<GrouponRuleVo> grouponRuleVoList = new ArrayList<>();
//        for (GrouponRulePo grouponRulePo : grouponRulePoList) {
//            Integer goodsId = grouponRulePo.getGoodsId();
//            System.out.println(goodsService.getGoodsPoById(goodsId));
//            String str = JacksonUtil.toJson(goodsService.getGoodsPoById(goodsId));
//            Map map = (Map) JSON.parse(str);
//            String data = map.get("data").toString();
//            GoodsPo goodsPo=new GoodsPo();
//            try{
//                goodsPo=new ObjectMapper().readValue(data, Goods.class);
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//            if (goodsPo == null){
//                continue;
//            }
//            GrouponRuleVo grouponRuleVo = new GrouponRuleVo();
//            grouponRuleVo.setGrouponRulePo(grouponRulePo);
//            grouponRuleVo.setGoodsPo(goodsPo);
//            grouponRuleVoList.add(grouponRuleVo);
//        }
//        return grouponRuleVoList;
//    }

//    public static List<PresaleRuleVo> presaleRulePacking(List<PresaleRule> presaleRuleList) {
//        List<PresaleRuleVo> presaleRuleVoList = new ArrayList<>();
//        for (PresaleRule presaleRule : presaleRuleList) {
//            Integer goodsId = presaleRule.getGoodsId();
//            String str = JacksonUtil.toJson(goodsService.getGoodsPoById(goodsId));
//            Map map = (Map) JSON.parse(str);
//            String data = map.get("data").toString();
//            GoodsPo goodsPo=new GoodsPo();
//            try{
//                goodsPo=new ObjectMapper().readValue(data, Goods.class);
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//            if (goodsPo == null) {
//                continue;
//            }
//            PresaleRuleVo presaleRuleVo = new PresaleRuleVo();
//            presaleRuleVo.setPresaleRule(presaleRule);
//            presaleRuleVo.setGoodsPo(goodsPo);
//            presaleRuleVoList.add(presaleRuleVo);
//        }
//        return presaleRuleVoList;
//    }
}
