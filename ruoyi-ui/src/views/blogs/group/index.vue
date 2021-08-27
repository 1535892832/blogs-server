<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="分类名称" prop="groupName">
        <el-input
          v-model="queryParams.groupName"
          placeholder="请输入分类名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否显示" prop="visible">
        <el-select v-model="queryParams.visible" placeholder="请选择是否显示" clearable size="small">
          <el-option
            v-for="dict in visibleOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
	    <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['blogs:group:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="groupList"
      row-key="groupId"
      default-expand-all
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="分类名称" prop="groupName" />
      <el-table-column label="排序" align="center" prop="orderNum" />
      <el-table-column label="是否显示" align="center" prop="visible" :formatter="visibleFormat" />
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="图标" align="center" prop="icon" />
      <el-table-column label="备注" align="center" prop="mark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['blogs:group:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['blogs:group:add']"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['blogs:group:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改笔记分类管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
        <el-form-item label="分类名称" prop="groupName">
          <el-input v-model="form.groupName" placeholder="请输入分类名称" />
        </el-form-item>
          </el-col>
          <el-col :span="24">
        <el-form-item label="父级分类" prop="parentId">
          <treeselect v-model="form.parentId" :options="groupOptions" :normalizer="normalizer" placeholder="请选择父级分类" />
        </el-form-item>
          </el-col>
          <el-col :span="24">
        <el-form-item label="排序" prop="orderNum">
          <el-input v-model="form.orderNum" placeholder="请输入排序" />
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="是否显示">
          <el-radio-group v-model="form.visible">
            <el-radio
              v-for="dict in visibleOptions"
              :key="dict.dictValue"
              :label="parseInt(dict.dictValue)"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="parseInt(dict.dictValue)"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
          </el-col>
          <el-col :span="24">
        <el-form-item label="图标">
          <imageUpload v-model="form.icon"/>
        </el-form-item>
          </el-col>
          <el-col :span="24">
        <el-form-item label="备注" prop="mark">
          <el-input v-model="form.mark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>

    </el-dialog>
  </div>
</template>

<script>
import { listGroup, getGroup, delGroup, addGroup, updateGroup, exportGroup } from "@/api/blogs/group";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Group",
  components: {
    Treeselect
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 笔记分类管理表格数据
      groupList: [],
      // 笔记分类管理树选项
      groupOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示字典
      visibleOptions: [],
      // 状态字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        groupName: null,
        visible: null,
        status: null,
        mark: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        groupName: [
          { required: true, message: "分类名称不能为空", trigger: "blur" }
        ],
        parentId: [
          { required: true, message: "父级分类id不能为空", trigger: "blur" }
        ],
        orderNum: [
          { required: true, message: "排序不能为空", trigger: "blur" }
        ],
        visible: [
          { required: true, message: "是否显示不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("note_visible").then(response => {
      this.visibleOptions = response.data;
    });
    this.getDicts("note_status").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询笔记分类管理列表 */
    getList() {
      this.loading = true;
      listGroup(this.queryParams).then(response => {
        this.groupList = this.handleTree(response.data, "groupId", "parentId");
        this.loading = false;
      });
    },
    /** 转换笔记分类管理数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.groupId,
        label: node.groupName,
        children: node.children
      };
    },
	/** 查询笔记分类管理下拉树结构 */
    getTreeselect() {
      listGroup().then(response => {
        this.groupOptions = [];
        const data = { groupId: 0, groupName: '顶级节点', children: [] };
        data.children = this.handleTree(response.data, "groupId", "parentId");
        this.groupOptions.push(data);
      });
    },
    // 是否显示字典翻译
    visibleFormat(row, column) {
      return this.selectDictLabel(this.visibleOptions, row.visible);
    },
    // 状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        groupId: null,
        groupName: null,
        parentId: null,
        orderNum: null,
        visible: 0,
        status: 0,
        icon: null,
        createBy: null,
        mark: null,
        createTime: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.groupId) {
        this.form.parentId = row.groupId;
      } else {
        this.form.parentId = 0;
      }
      this.open = true;
      this.title = "添加笔记分类管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      if (row != null) {
        this.form.parentId = row.groupId;
      }
      getGroup(row.groupId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改笔记分类管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.groupId != null) {
            updateGroup(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGroup(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('是否确认删除笔记分类管理编号为"' + row.groupId + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delGroup(row.groupId);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    }
  }
};
</script>
