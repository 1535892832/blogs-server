import request from '@/utils/request'

// 查询笔记分类管理列表
export function listGroup(query) {
  return request({
    url: '/blogs/group/list',
    method: 'get',
    params: query
  })
}

export function treeselect() {
  return request({
    url: '/blogs/group/treeselect',
    method: 'get'
  })
}

// 查询笔记分类管理详细
export function getGroup(groupId) {
  return request({
    url: '/blogs/group/' + groupId,
    method: 'get'
  })
}

// 新增笔记分类管理
export function addGroup(data) {
  return request({
    url: '/blogs/group',
    method: 'post',
    data: data
  })
}

// 修改笔记分类管理
export function updateGroup(data) {
  return request({
    url: '/blogs/group',
    method: 'put',
    data: data
  })
}

// 删除笔记分类管理
export function delGroup(groupId) {
  return request({
    url: '/blogs/group/' + groupId,
    method: 'delete'
  })
}

// 导出笔记分类管理
export function exportGroup(query) {
  return request({
    url: '/blogs/group/export',
    method: 'get',
    params: query
  })
}
