package com.ubisys.drone.modules.base.controller.scaffold;

import cn.hutool.core.bean.BeanUtil;
import com.ubisys.drone.basetemplate.DroneBaseController;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.PageUtil;
import com.ubisys.drone.common.vo.PageVo;
import com.ubisys.drone.modules.base.entity.DictData;
import com.ubisys.drone.modules.base.entity.DictLine;
import com.ubisys.drone.modules.base.model.ZTreeModel;
import com.ubisys.drone.modules.base.service.DictDataService;
import com.ubisys.drone.modules.base.service.DictLineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author miaomiao
 */
@Slf4j
@RestController
@Api(description = "字典接口")
@RequestMapping("/drone/dictLine")
@Transactional
public class DictLineController extends DroneBaseController<DictLine, Integer> {

    @Autowired
    private DictLineService dictLineService;

    @Override
    public DictLineService getService() {
        return dictLineService;
    }

    @Autowired
    private DictDataService dictDataService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ApiOperation(value = "获取全部数据")
    public AjaxResponse getAll() {
        List<DictLine> list = dictLineService.getAll();
        List<ZTreeModel> layuiTreeModels=new ArrayList<ZTreeModel>();
        for (DictLine dictLine : list) {
            ZTreeModel treeModel =new ZTreeModel();
            BeanUtil.copyProperties(dictLine,treeModel);
            treeModel.setName(dictLine.getTitle());
            layuiTreeModels.add(treeModel);
        }
        return AjaxResponse.success(layuiTreeModels);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加")
    public AjaxResponse add(@ModelAttribute DictLine dict) {

        if (dictLineService.findByType(dict.getType()) != null) {
            return AjaxResponse.failed("字典类型Type已存在");
        }
        dictLineService.save(dict);
        //

        return AjaxResponse.success("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "编辑")
    public AjaxResponse edit(@ModelAttribute DictLine dict) {

        DictLine old = dictLineService.get(dict.getId());
        // 若type修改判断唯一
        if (!old.getType().equals(dict.getType()) && dictLineService.findByType(dict.getType()) != null) {
            return AjaxResponse.failed("字典类型Type已存在");
        }
        dictLineService.update(dict);
        return AjaxResponse.success("编辑成功");
    }

    @RequestMapping(value = "/delByIds/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "通过id删除")
    public AjaxResponse delAllByIds(@PathVariable String id) {


        DictLine dict = dictLineService.get(Integer.valueOf(id));
        dictLineService.delete(dict.getId());
        dictDataService.deleteByDictId(dict.getId());
        // 删除缓存
        redisTemplate.delete("dictData::" + dict.getType());
        return AjaxResponse.success("删除成功！");
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "搜索字典")
    public AjaxResponse searchPermissionList(@RequestParam String key) {
        List<DictLine> list = dictLineService.findByTitleOrTypeLike(key);
        return AjaxResponse.success(list);
    }


    @RequestMapping(value = "/getByCondition", method = RequestMethod.POST)
    @ApiOperation(value = "多条件分页获取字典属性列表")
    public AjaxResponse getByCondition(@ModelAttribute DictData dictData,
                                       @ModelAttribute PageVo pageVo) {

        Page<DictData> page = dictDataService.findByCondition(dictData, PageUtil.initPage(pageVo));
        AjaxResponse response = AjaxResponse.success();
        response.setRows(page.getContent());
        response.setTotal((int) page.getTotalElements());
        return response;
    }

    @RequestMapping(value = "/getByType/{type}", method = RequestMethod.POST)
    @ApiOperation(value = "通过类型获取")
    public AjaxResponse getByType(@PathVariable String type) {

        DictLine dict = dictLineService.findByType(type);
        if (dict == null) {
            return AjaxResponse.failed("字典类型Type不存在");
        }
        List<DictData> list = dictDataService.findByDictId(dict.getId());
        return AjaxResponse.success(list);
    }

    @RequestMapping(value = "/addDictData", method = RequestMethod.POST)
    @ApiOperation(value = "添加字典属性")
    public AjaxResponse addDictData(@ModelAttribute DictData dictData) {

        DictLine dict = dictLineService.get(dictData.getDictId());
        if (dict == null) {
            return AjaxResponse.failed("字典类型id不存在");
        }
        dictDataService.save(dictData);
        return AjaxResponse.success("添加成功");
    }

    @RequestMapping(value = "/editDictData", method = RequestMethod.POST)
    @ApiOperation(value = "编辑字典属性")
    public AjaxResponse editDictData(@ModelAttribute DictData dictData) {
        dictDataService.update(dictData);
        return AjaxResponse.success("编辑成功");
    }

    @RequestMapping(value = "/delDataByIds/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过id删除字典属性表")
    public AjaxResponse delDataByIds(@PathVariable String[] ids) {
        for (String id : ids) {
            DictLine dict = dictLineService.get(Integer.valueOf(id));
            dictDataService.delete(dict.getId());
        }
        return AjaxResponse.success("批量通过id删除数据成功");
    }


}
