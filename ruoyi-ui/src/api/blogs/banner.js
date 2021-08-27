import request from '@/utils/request'

// 查询Banner管理列表
export function listBanner(query) {
  return request({
    url: '/blogs/banner/list',
    method: 'get',
    params: query
  })
}

// 查询Banner管理详细
export function getBanner(id) {
  return request({
    url: '/blogs/banner/' + id,
    method: 'get'
  })
}

// 新增Banner管理
export function addBanner(data) {
  return request({
    url: '/blogs/banner',
    method: 'post',
    data: data
  })
}

// 修改Banner管理
export function updateBanner(data) {
  return request({
    url: '/blogs/banner',
    method: 'put',
    data: data
  })
}

// 删除Banner管理
export function delBanner(id) {
  return request({
    url: '/blogs/banner/' + id,
    method: 'delete'
  })
}

// 导出Banner管理
export function exportBanner(query) {
  return request({
    url: '/blogs/banner/export',
    method: 'get',
    params: query
  })
}