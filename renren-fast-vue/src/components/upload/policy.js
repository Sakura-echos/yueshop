import http from '@/utils/httpRequest.js'
export function policy() {
   return  new Promise((resolve,reject)=>{
        http({
            url: http.adornUrl("/thirdparty/oss/policy"),//指定上传策略的获取接口
            method: "get",
            params: http.adornParams({})
        }).then(({ data }) => {
            resolve(data);
        })
    });
}