/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package org.jbpm.taskinstances.impl;

import java.util.List;
import org.jbpm.api.Task;
import org.jbpm.api.TaskInstance;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.SequenceFlow;
import org.jbpm.factories.TaskInstanceFactory;
import org.jbpm.tasks.services.ProcessEventSupportService;
import org.jbpm.tasks.services.ProcessEventSupportServiceFactory;

/**
 *
 * @author salaboy
 */
public abstract class AbstractTaskInstance implements TaskInstance {

    protected ProcessInstance processInstance;
    protected Task task;
    protected ProcessEventSupportService eventService;

    public AbstractTaskInstance(ProcessInstance processInstance, Task task) {
        this.processInstance = processInstance;
        this.task = task;
        eventService = (ProcessEventSupportService)processInstance.getService("event-service");
    }
    
    
    
    @Override
    public final void trigger(TaskInstance from, String type) {

        //Fire before TASK Triggered

        eventService.fireBeforeTaskTriggered(this);

        internalTrigger(from, type);

        //Fire after TASK Triggered
        eventService.fireAfterTaskTriggered(this);

    }

    @Override
    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    public abstract void internalTrigger(TaskInstance from, String type);

    private ProcessEventSupportService getProcessEventSupportService() {
        return ProcessEventSupportServiceFactory.getService();
    }

    protected void triggerCompleted(String type, boolean remove) {
        if (remove) {

            processInstance.getTaskContainer().removeTaskInstance(this);
        }
        Task task = getTask();
        List<SequenceFlow> flows = null;
        if (task != null) {
            flows = task.getOutgoingFlows(type);
        }
        if (flows == null || flows.isEmpty()) {
            processInstance.getTaskContainer().taskInstanceCompleted(this, type);
        } else {
            for (SequenceFlow flow : flows) {
                triggerConnection(flow);
            }
        }
    }

    protected void triggerConnection(SequenceFlow flow) {

        eventService.fireBeforeTaskLeft(this);
        this.processInstance.getTaskContainer().addTaskInstance(TaskInstanceFactory.newTaskInstance(this.processInstance, flow.getTo()));
        // trigger next TASK
        this.processInstance.getTaskContainer().getTaskInstance(flow.getTo()).trigger(this, flow.getToType());

        eventService.fireAfterTaskLeft(this);

    }

    @Override
    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public Task getTask() {
        return this.task;
    }
}
