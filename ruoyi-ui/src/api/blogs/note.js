import request from '@/utils/request'

// 查询笔记管理列表
export function listNote(query) {
  return request({
    url: '/blogs/note/list',
    method: 'get',
    params: query
  })
}

// 查询笔记管理详细
export function getNote(id) {
  return request({
    url: '/blogs/note/' + id,
    method: 'get'
  })
}

// 新增笔记管理
export function addNote(data) {
  return request({
    url: '/blogs/note',
    method: 'post',
    data: data
  })
}

// 修改笔记管理
export function updateNote(data) {
  return request({
    url: '/blogs/note',
    method: 'put',
    data: data
  })
}

export function changeNoteVisible(id, visible) {
  const data = {
    id,
    visible
  }
  return request({
    url: '/blogs/note/changeVisible',
    method: 'put',
    data: data
  })
}

export function changeOfficialRecommend(id, isOfficialRecommend) {
  const data = {
    id,
    isOfficialRecommend
  }
  return request({
    url: '/blogs/note/changeOfficialRecommend',
    method: 'put',
    data: data
  })
}

// 删除笔记管理
export function delNote(id) {
  return request({
    url: '/blogs/note/' + id,
    method: 'delete'
  })
}

// 导出笔记管理
export function exportNote(query) {
  return request({
    url: '/blogs/note/export',
    method: 'get',
    params: query
  })
}
