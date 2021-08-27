<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入banner标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item label="是否外链" prop="isOutside">-->
      <!--        <el-select v-model="queryParams.isOutside" placeholder="请选择是否外链" clearable size="small">-->
      <!--          <el-option-->
      <!--            v-for="dict in isOutsideOptions"-->
      <!--            :key="dict.dictValue"-->
      <!--            :label="dict.dictLabel"-->
      <!--            :value="dict.dictValue"-->
      <!--          />-->
      <!--        </el-select>-->
      <!--      </el-form-item>-->
      <el-form-item label="状态" prop="visible">
        <el-select v-model="queryParams.visible" placeholder="状态" clearable size="small">
          <el-option
            v-for="dict in visibleOptions"
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
          v-hasPermi="['blogs:banner:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['blogs:banner:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['blogs:banner:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['blogs:banner:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bannerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
<!--      <el-table-column label="id" align="center" prop="id"/>-->
      <el-table-column label="标题" align="center" prop="title"/>
      <!--      <el-table-column label="banner图" align="center" prop="imgUrl" />-->
      <el-table-column label="是否外链" align="center" prop="isOutside" :formatter="isOutsideFormat"/>
      <el-table-column show-overflow-tooltip label="外链链接" align="center">
        <template slot-scope="scope">
          <a :href="scope.row.outsideHref" target="_blank">{{scope.row.outsideHref}}</a>
        </template>
      </el-table-column>
      <el-table-column label="排序序号" align="center" prop="orderNum"/>
      <el-table-column label="状态" align="center" prop="visible" :formatter="visibleFormat"/>
      <el-table-column label="用户名" align="center" prop="userName"/>
      <!--      <el-table-column label="用户id" align="center" prop="userId" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['blogs:banner:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['blogs:banner:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改Banner管理对话框 -->
    <el-dialog :title="title" width="50%" :visible.sync="open" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24" :xs="24">
            <el-form-item label="标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入banner标题"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>

          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.visible">
                <el-radio
                  v-for="dict in visibleOptions"
                  :key="dict.dictValue"
                  :label="parseInt(dict.dictValue)"
                >{{dict.dictLabel}}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="是否外链">
              <el-radio-group v-model="form.isOutside">
                <el-radio
                  v-for="dict in isOutsideOptions"
                  :key="dict.dictValue"
                  :label="parseInt(dict.dictValue)"
                >{{dict.dictLabel}}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="排序序号" prop="orderNum">
              <el-input v-model="form.orderNum" placeholder="请输入排序序号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.isOutside == 1" label="外链链接" prop="outsideHref">
              <el-input v-model="form.outsideHref" placeholder="请输入外链链接"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
        <el-form-item label="banner图">
          <imageUpload :limit="1" v-model="form.imgUrl"/>
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
  import {listBanner, getBanner, delBanner, addBanner, updateBanner, exportBanner} from "@/api/blogs/banner";

  export default {
    name: "Banner",
    data() {
      return {
        // 遮罩层
        loading: true,
        // 导出遮罩层
        exportLoading: false,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // Banner管理表格数据
        bannerList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 是否外链字典
        isOutsideOptions: [],
        // 是否显示 0显示 1不显示字典
        visibleOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          title: null,
          isOutside: null,
          visible: null,
          userName: null,
          userId: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          title: [
            {required: true, message: "banner标题不能为空", trigger: "blur"}
          ],
          imgUrl: [
            {required: true, message: "banner图不能为空", trigger: "blur"}
          ],
          orderNum: [
            {required: true, message: "排序序号不能为空", trigger: "blur"}
          ],
          visible: [
            {required: true, message: "是否显示 0显示 1不显示不能为空", trigger: "blur"}
          ],
          userName: [
            {required: true, message: "用户名不能为空", trigger: "blur"}
          ],
          userId: [
            {required: true, message: "用户id不能为空", trigger: "blur"}
          ],
        }
      };
    },
    created() {
      this.getList();
      this.getDicts("banner_is_outside").then(response => {
        this.isOutsideOptions = response.data;
      });
      this.getDicts("common_visible").then(response => {
        this.visibleOptions = response.data;
      });
    },
    methods: {
      /** 查询Banner管理列表 */
      getList() {
        this.loading = true;
        listBanner(this.queryParams).then(response => {
          this.bannerList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 是否外链字典翻译
      isOutsideFormat(row, column) {
        return this.selectDictLabel(this.isOutsideOptions, row.isOutside);
      },
      // 是否显示 0显示 1不显示字典翻译
      visibleFormat(row, column) {
        return this.selectDictLabel(this.visibleOptions, row.visible);
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          title: null,
          imgUrl: null,
          isOutside: 0,
          outsideHref: null,
          orderNum: null,
          visible: 0,
          userName: null,
          userId: null,
          createTime: null,
          updateTime: null
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加Banner管理";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getBanner(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改Banner管理";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateBanner(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addBanner(this.form).then(response => {
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
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除Banner管理编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delBanner(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有Banner管理数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportBanner(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {
        });
      }
    }
  };
</script>
