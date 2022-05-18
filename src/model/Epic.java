package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<SubTask> subTasks;

    public Epic(String name, String description, int id, Status status) {
        super(name, description, id, status);
        this.subTasks = new ArrayList<>();
    }

    public List<SubTask> getListSubTask() {
        return subTasks;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public LocalDateTime getEndTime(){
        return findLateEndTimeSubTasks();
    }

    private  LocalDateTime findLateEndTimeSubTasks(){
        LocalDateTime lateEndTime = subTasks.get(0).getEndTime();
        if(subTasks.size() > 1){
            for(int i = 1; i< subTasks.size(); i++){
                if(lateEndTime == null)
                    lateEndTime = subTasks.get(i).getEndTime();
                else if (subTasks.get(i).getEndTime() != null
                && subTasks.get(i).getEndTime().isAfter(lateEndTime)){
                    lateEndTime = subTasks.get(i).getEndTime();
                }
            }
        }
        return lateEndTime;
    }

    private LocalDateTime findEarlyStartTimeSubTasks(){
        LocalDateTime earlyStartTime = subTasks.get(0).getStartTime();
        if(subTasks.size() > 1)
            for(int i = 1; i < subTasks.size(); i++)
                if(earlyStartTime ==null)
                    earlyStartTime = subTasks.get(i).getStartTime();
        else if(subTasks.get(i).getStartTime() != null
                && subTasks.get(i).getStartTime().isBefore(earlyStartTime))
            earlyStartTime = subTasks.get(i).getStartTime();
        return earlyStartTime;
    }

    public void updateStartTimeAndDuration(){
        duration = null;
        startTime = null;
        if(!subTasks.isEmpty()){
            for(SubTask subTask : subTasks){
                if(subTask.getDuration() != null){
                    if(duration == null){
                        duration = subTask.getDuration();
                    } else {
                        duration = duration.plus(subTask.getDuration());
                    }
                }
            }
            startTime = findEarlyStartTimeSubTasks();
        }
    }
}
