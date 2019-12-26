package com._520.bankerAlgorithm.serlvet;

import com._520.bankerAlgorithm.entity.BankerAlgorithm;
import com._520.bankerAlgorithm.entity.Process;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/init")
public class InitServlet extends HttpServlet {
    // 所有进程
    private static Map<String, Process> processes;
    // 各类资源总数
    private static Map<String, Integer> totalResources;
    private static BankerAlgorithm banker;
    @Override
    public void init(){
        processes = new HashMap<>();
        totalResources = new HashMap<>();
        banker = BankerAlgorithm.getInstance();
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String key = req.getParameter("cmd");
        if ("resource".equals(key)){
            this.initReources(req, resp);
        }else if ("process".equals(key)){
            this.initProcess(req,resp);
        }else if ("showSafe".equals(key)){
            this.showSafe(req, resp);
        }else if ("request".equals(key)){
            this.requestAv(req, resp);
        }else if ("remove".equals(key)){
            this.remove(req, resp);
        } else {
            this.show(req, resp);
        }
    }

    private void requestAv(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 资源数
        int size = totalResources.size();
        String reqName = req.getParameter("reqName");
        System.out.println("请求资源名称:" + reqName);
        int[] request = new int[size];

        for (int i = 0; i < size; i++) {
            request[i] = Integer.valueOf(req.getParameter("reqCount" + (i + 1)));
        }
        System.out.println("请求的资源：" + Arrays.toString(request));
        // 发出请求
        String msg = banker.request(reqName, request, processes, totalResources);
        // 传递运行结果
        req.setAttribute("msg", msg);
        req.getRequestDispatcher("/jsp/success.jsp").forward(req, resp);
    }

    // 安全性算法检查
    private void showSafe(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Process> safeSerial = banker.getSafeSerial(processes, totalResources);
        System.out.println("-------------------------");
        if (safeSerial == null){
            // 传递运行结果
            req.setAttribute("msg","不存在安全序列！");
            req.getRequestDispatcher("/jsp/success.jsp").forward(req, resp);
            return;
        }
        List<String> strings = new ArrayList<>();
        // 存放安全序列
        StringBuilder stringBuilder = new StringBuilder();
        // 将进程转换为字符串
        for (Process p : safeSerial){
            getStrSafe(strings, p);
        }
        stringBuilder.append("安全序列为：");
        for (int i = 0; i < safeSerial.size(); i++) {
            if (i < safeSerial.size() - 1){
                stringBuilder.append(safeSerial.get(i).getName());
                stringBuilder.append("  -->  ");
            }else {
                stringBuilder.append(safeSerial.get(i).getName());
            }
        }
        //
        req.setAttribute("safeSerial", strings);
        req.setAttribute("string", stringBuilder.toString());
        req.getRequestDispatcher("/jsp/showSafe.jsp").forward(req, resp);
    }

    private void getStrSafe(List<String> list, Process process) {
        list.add(process.getName());
        list.add(Arrays.toString(process.getWork()));
        list.add(Arrays.toString(process.getNeed()));
        list.add(Arrays.toString(process.getAllocation()));
        list.add(Arrays.toString(process.getWorkAndAllocation()));
        if (process.isFinish()){
            list.add("true");
        }else {
            list.add("flase");
        }
    }

    // 清除所有的进程，添加新的资源
    private void remove(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processes.clear();
        req.getSession().removeAttribute("processNum");
        req.setAttribute("msg", "删除成功，请去添加新的资源！");
        req.getRequestDispatcher("/jsp/initResources.jsp").forward(req, resp);
    }

    // 显示资源分配情况
    private void show(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("total = " + totalResources);
        List<String> resourcesName = banker.getResourcesName(totalResources);
        System.out.println("资源名称 = " + resourcesName);
        List<String> list = new ArrayList<>();
        // 将进程加到list中
        for (Map.Entry<String, Process> map: processes.entrySet()) {
            getStr(list, map.getValue());
        }

        System.out.println("数组：" + list);
        // 传进程
        req.setAttribute("processes",list);
        // 传递资源名称
        req.setAttribute("resourceName", resourcesName);

        // 传递可用资源
        req.setAttribute("available", Arrays.toString(getAvailable(processes)));
        req.getRequestDispatcher("/jsp/show.jsp").forward(req, resp);
    }

    private void getStr(List<String> list, Process process) {
        list.add(process.getName());
        list.add(Arrays.toString(process.getMax()));
        list.add(Arrays.toString(process.getAllocation()));
        list.add(Arrays.toString(process.getNeed()));
    }


    private void initProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // 资源数
        int size = totalResources.size();
        // 接收用户传入的数据
        String processName = req.getParameter("processName");
        System.out.println("进程名称：" + processName);
        int[] max = new int[size];
        int[] allocation = new int[size];
        String maxCount = "maxCount";
        String alCount = "allocationCount";
        for (int i = 1; i <= size; i++) {
            Integer m = Integer.valueOf(req.getParameter(maxCount + i));
            Integer al = Integer.valueOf(req.getParameter(alCount + i));
            max[i - 1] = m;
            allocation[i - 1] = al;
        }

        System.out.println("Max = " + Arrays.toString(max));
        System.out.println("Allocation = " + Arrays.toString(allocation));
        processes.put(processName, new Process(processName, max, allocation));
        // 传递进程数
        req.getSession().setAttribute("processNum", processes.size());
        req.getRequestDispatcher("/jsp/continueOrNotProcess.jsp").forward(req, resp);
    }

    private void initReources(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("resourceName");
        Integer number = Integer.valueOf( req.getParameter("resourceCount"));
        totalResources.put(name, number);
        System.out.println("资源数：" + totalResources.size());
        // 将资源数存到session中，以便在创建进程的时候使用
        req.getSession().setAttribute("resourceSize", totalResources.size());
        req.getRequestDispatcher("/jsp/continueOrNot.jsp").forward(req, resp);
    }

    // 获取剩余的资源数
    private int[] getAvailable(Map<String, Process> processes) {
        int[] available = new int[totalResources.size()];
        initAv(available);
        for (Map.Entry<String, Process> map : processes.entrySet()){
            int[] allocation = map.getValue().getAllocation();
            for (int j = 0; j < available.length; j++) {
                available[j] -= allocation[j];
            }
        }
        return available;
    }
    // 将map中的值取出
    private void initAv(int[] available) {
        int count = 0;
        for (Map.Entry<String, Integer> map : totalResources.entrySet()) {
            available[count++] = map.getValue();
        }
    }
}
