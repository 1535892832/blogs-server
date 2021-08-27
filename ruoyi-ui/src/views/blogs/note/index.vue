<template>
  <div class="app-container">

    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="noteGroupName"
            placeholder="请输入分类名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="noteGroupOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>

      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="标题" prop="title">
            <el-input
              v-model="queryParams.title"
              placeholder="请输入标题"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
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
              v-hasPermi="['blogs:note:add']"
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
              v-hasPermi="['blogs:note:edit']"
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
              v-hasPermi="['blogs:note:remove']"
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
              v-hasPermi="['blogs:note:export']"
            >导出
            </el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="noteList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="标题" align="center" prop="title"/>
          <el-table-column label="浏览量" align="center" prop="pageViewNum"/>
          <el-table-column label="收藏" align="center" prop="collectNum"/>
<!--          <el-table-column label="内容" align="center" prop="content"/>-->
          <el-table-column label="点赞数" align="center" prop="likeNum"/>
          <el-table-column label="笔记类型" align="center" prop="type" :formatter="noteTypeFormat"/>
          <el-table-column label="状态" align="center" key="visible">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.visible"
                :active-value="0"
                :inactive-value="1"
                @change="handleVisibleChange(scope.row)"
              ></el-switch>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['blogs:note:edit']"
              >修改
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['blogs:note:remove']"
              >删除
              </el-button>
              <el-button
                v-if="scope.row.isOfficialRecommend == 0"
                size="mini"
                type="text"
                icon="el-icon-coordinate"
                @click="handleChangeOfficialRecommend(scope.row,1)"
                v-hasPermi="['blogs:note:officialRecommend']"
              >
                推荐
              </el-button>
              <el-button
                v-else
                size="mini"
                type="text"
                icon="el-icon-coordinate"
                @click="handleChangeOfficialRecommend(scope.row,0)"
                v-hasPermi="['blogs:note:officialRecommend']"
              >
                取消推荐
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
      </el-col>
    </el-row>

    <!-- 添加或修改笔记管理对话框 -->
    <el-dialog :fullscreen="true" :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="24">
          <el-col :span="12" :xs="12">
            <el-form-item label="分类" prop="groupId">
              <treeselect :disable-branch-nodes="true" v-model="form.groupId" :options="noteGroupOptions"
                          :show-count="true"
                          placeholder="请选择笔记分类"/>
            </el-form-item>
          </el-col>
          <el-col :span="12" :xs="12">
            <el-form-item label="标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入标题"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="24">
          <el-col :span="12" :xs="12">
            <el-form-item label="笔记类型" prop="type">
              <el-radio-group v-model="form.type">
                <el-radio
                  v-for="dict in noteTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12" :xs="12">
            <el-form-item label="显示" prop="visible">
              <el-radio-group v-model="form.visible">
                <el-radio
                  v-for="dict in visibleOptions"
                  :label="parseInt(dict.dictValue)"
                  :key="dict.dictValue"
                >{{dict.dictLabel}}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="24">
          <el-col :span="12" :xs="12">
            <el-form-item label="描述" prop="description">
              <el-input v-model="form.description" placeholder="请输入描述" type="textarea" :rows="8"/>
            </el-form-item>
          </el-col>
          <el-col :span="12" :xs="12">
            <el-form-item label="封面" prop="coverPicture">


              <imageUpload :limit="1" v-model="form.coverPicture"/>

            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="内容">
          <editor v-show="form.type == 'word'" v-model="wordContent" :min-height="500"/>

          <!--     此处应为md编辑器         -->
          <mavon-editor v-show="form.type == 'markdown'" v-model="markdownContent" style="min-height: 500px">

          </mavon-editor>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {listNote, getNote, delNote, addNote, updateNote, exportNote, changeNoteVisible,changeOfficialRecommend} from "@/api/blogs/note";
  import {mavonEditor} from 'mavon-editor'
  import 'mavon-editor/dist/css/index.css'
  import {getToken} from '@/utils/auth'
  import {treeselect} from "@/api/blogs/group";
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";

  export default {
    name: "Note",
    components: {Treeselect, mavonEditor},
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
        // 笔记管理表格数据
        noteList: [],
        // 笔记类型选项
        noteTypeOptions: [],

        visibleOptions: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,

        imageUrl: '',

        wordContent: '',
        markdownContent: '',

        headers: {
          'Authorization': 'Bearer ' + getToken()
        },
        noteGroupName: undefined,
        noteGroupOptions: undefined,
        uploadUrl: process.env.VUE_APP_BASE_API + '/common/upload',
        defaultProps: {
          children: "children",
          label: "label"
        },
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          groupId: null,
          title: null,
          authorId: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          groupId: [
            {required: true, message: "分类id不能为空", trigger: "blur"}
          ],
          title: [
            {required: true, message: "标题不能为空", trigger: "blur"}
          ],
          content: [
            {required: true, message: "内容不能为空", trigger: "blur"}
          ]
        }
      };
    },
    created() {
      this.getList();
      this.getTreeselect();
      this.getDicts("note_type").then(response => {
        this.noteTypeOptions = response.data;
      });
      this.getDicts("common_visible").then(response => {
        this.visibleOptions = response.data;
      });
    },
    methods: {
      handleChangeOfficialRecommend(row,isOfficialRecommend){
        let text = isOfficialRecommend == "0" ? "取消推荐" : "推荐";
        this.$confirm('确认要"' + text + '当前笔记吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return changeOfficialRecommend(row.id, isOfficialRecommend);
        }).then(() => {
          this.msgSuccess(text + "成功");
          this.getList()
        }).catch(function () {
          row.isOfficialRecommend = row.isOfficialRecommend === "0" ? "1" : "0";
        });
      },

      noteTypeFormat(row) {
        return this.selectDictLabel(this.noteTypeOptions, row.type);
      },

      handleVisibleChange(row) {
        let text = row.visible === "0" ? "停用" : "启用";
        this.$confirm('确认要"' + text + '当前笔记吗?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return changeNoteVisible(row.id, row.visible);
        }).then(() => {
          this.msgSuccess(text + "成功");
        }).catch(function () {
          row.visible = row.visible === "0" ? "1" : "0";
        });
      },

      /** 文件上传成功  **/
      handleUploadSuccess(response, file) {
        console.log(file)
        if (response.code == 200) {
          this.imageUrl = URL.createObjectURL(file.raw);
          this.form.coverPicture = response.fileName;
          this.msgSuccess("封面上传成功");
          return;
        }
        this.msgError("封面上传失败");
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;

        this.dialogVisible = true;
      },
      /** 文件上传失败  **/
      handleUploadError(err, file) {
        this.msgError("封面上传失败");
      },
      /** 文件超出限制 **/
      handleExceed() {
        this.msgError("文件个数超出限制");
      },
      /** 查询笔记管理列表 */
      getList() {
        this.loading = true;
        listNote(this.queryParams).then(response => {
          this.noteList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      /** 查询部门下拉树结构 */
      getTreeselect() {
        treeselect().then(response => {
          this.noteGroupOptions = response.data;
        });
      },
      // 筛选节点
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      // 节点单击事件
      handleNodeClick(data) {
        this.queryParams.groupId = data.id;
        this.getList();
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
          groupId: null,
          title: null,
          description: null,
          coverPicture: null,
          pageViewNum: null,
          collectNum: null,
          type: 'markdown',
          visible: 0,
          content: null,
          likeNum: null,
          authorId: null,
          createTime: null,
          updateTime: null
        };
        this.imageUrl='',
        this.wordContent = '',
          this.markdownContent = '',
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
        this.title = "添加笔记管理";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getNote(id).then(response => {
          this.form = response.data;
          if (this.form.type == 'word') {
            this.wordContent = this.form.content;
          } else if (this.form.type == 'markdown') {
            this.markdownContent =  this.form.content ;
          }
          this.open = true;
          this.title = "修改笔记管理";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.type == 'word') {
              this.form.content = this.wordContent;
            } else if (this.form.type == 'markdown') {
              this.form.content = this.markdownContent;
            }
            if (this.form.id != null) {
              updateNote(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {

              addNote(this.form).then(response => {
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
        this.$confirm('是否确认删除笔记管理编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delNote(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有笔记管理数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportNote(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {
        });
      }
    }
  };
</script>
<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }

  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
