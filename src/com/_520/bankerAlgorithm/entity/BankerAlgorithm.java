package com._520.bankerAlgorithm.entity;

import java.util.*;

public class BankerAlgorithm {

   private static BankerAlgorithm INSTANCE = new BankerAlgorithm();

   public static BankerAlgorithm getInstance(){
       return INSTANCE;
   }

    /**
     * 请求
     *
     * @param processName 发出请求的进程名称
     * @param request     请求的资源数组
     */
    public String request(String processName, int[] request, Map<String, Process> processes, Map<String, Integer> totalResources) {
        System.out.println(processName + " 请求资源数：" + Arrays.toString(request));
        int[] available = getAvailable(processes, totalResources);
        // 剩余的资源总数
        int[] av = Arrays.copyOf(available, available.length);
        // 判断请求资源是否大于剩余的资源总数
        for (int i = 0; i < request.length; i++) {
            if (request[i] > av[i]) {
                return "剩余资源不能满足你的请求！";
            }
        }

        // 请求的进程
        Process current = processes.get(processName);
        // 已分配资源
        int[] al = current.getAllocation();
        // 需求
        int[] need = current.getNeed();
        // 判断请求资源是否大于需求
        for (int i = 0; i < need.length; i++) {
            if (request[i] > need[i]) {
                return "请求数过多。";
            }
        }
        // 记录分配前的资源
        int[] oldAl = Arrays.copyOf(al, al.length);
        int[] oldNeed = Arrays.copyOf(need, need.length);
        // 分配资源
        for (int i = 0; i < al.length; i++) {
            al[i] += request[i];
            need[i] -= request[i];
        }
        // 判断是否存在安全序列
        if (getSafeSerial(processes, totalResources) != null) {
            // 存在安全序列
            current.setAllocation(al);
            current.setNeed(need);
            return "存在安全序列，可以分配！";
        } else {
            current.setAllocation(oldAl);
            current.setNeed(oldNeed);
            return "找不到安全序列，不能分配！";
        }
    }

    // 返回资源名称
    public List<String> getResourcesName(Map<String, Integer> totalResources){
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Integer> map : totalResources.entrySet()){
            list.add(map.getKey());
        }
        return list;
    }


    // 申请资源时的安全性检查
    public List<Process> getSafeSerial(Map<String, Process> processMap, Map<String, Integer> totalResources) {

        Map<String, Process> map = new HashMap<>(processMap);
        // 存放安全序列
        List<Process> safeProcess = new ArrayList<>();
        while (!map.isEmpty()){
            // 每次更新可利用资源
            int[] available = getAvailable(map, totalResources);
            // 检查是否有满足的进程
            Process process = findSafeProcess(map, available);
            // 没有找到符合的进程，说明不安全，直接返回
            if (process == null){
                return null;
            }else {// 找到符合的进程

                // 设置 Work
                process.setWork(available);
                // 计算 WorkAndAllocation
                process.setWorkAndAllocation(addTwoArrays(process.getWork(), process.getAllocation()));
                // 设置状态
                process.setFinish(true);
                // 把当前进程加到安全序列中
                safeProcess.add(process);
                // 删除当前进程，下次遍历的时候不需要判断这个进程
                map.remove(process.getName());
            }

        }

        return safeProcess;

    }

    // 寻找符合的进程
    private static Process findSafeProcess(Map<String, Process> processMap, int[] available) {
        for (Map.Entry<String, Process> entry : processMap.entrySet()) {
            int[] av = Arrays.copyOf(available, available.length);
            Process p = entry.getValue();
            boolean flag = true;
            for (int i = 0; i < av.length; i++) {
                if (p.getNeed()[i] > av[i]){
                    flag = false;
                    break;
                }
            }
            if (flag){
                return p;
            }
        }

        return null;
    }

    // 获取剩余的资源数
    private int[] getAvailable(Map<String, Process> processes, Map<String, Integer> totalResources) {
        int[] available = new int[totalResources.size()];
        int count = 0;
        for (Map.Entry<String, Integer> map : totalResources.entrySet()) {
            available[count++] = map.getValue();
        }
        for (Map.Entry<String, Process> map : processes.entrySet()){
            int[] allocation = map.getValue().getAllocation();
            for (int j = 0; j < available.length; j++) {
                available[j] -= allocation[j];
            }
        }
        return available;
    }
    // 计算workAndAllocation
    private static int[] addTwoArrays(int[] arr1, int[] arr2) {
        int[] arr = new int[arr1.length];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr1[i] + arr2[i];
        }
        return arr;
    }
}
