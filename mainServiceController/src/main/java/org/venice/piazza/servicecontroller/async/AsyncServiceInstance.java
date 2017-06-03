/**
 * Copyright 2016, RadiantBlue Technologies, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package org.venice.piazza.servicecontroller.async;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import model.status.StatusUpdate;

/**
 * This POJO Model represents a single run of an Asynchronous User Service. This marks the Unique ID of the Piazza
 * Service, the Unique ID of the User Service instance that was reported back by the User Service, and the current
 * status of the User Service. This also stores the date that the Service Controller last pinged this instance for
 * status.
 * <p/>
 * This is used internally by Service Controller to track the state of all running Asynchronous Jobs.
 * 
 * @author Patrick.Doody
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AsyncServiceInstance {
	private String jobId;
	private String serviceId;
	private String instanceId;
	private StatusUpdate status;
	private String outputType;
	private Integer numberErrorResponses;
	@JsonIgnore
	private DateTime lastCheckedOn;

	public AsyncServiceInstance() {
		// Required for serialization
	}

	/**
	 * 
	 * @param jobId
	 *            The unique Job ID for this Instance. Generate by UUIDGen.
	 * @param serviceId
	 *            The unique ID of the User Service
	 * @param instanceId
	 *            The unique ID of the execution instance for this User Service. Generated by User Service
	 * @param status
	 *            The current status of this execution. @see StatusUpdate
	 * @param outputType
	 *            The output mime type of this instance execution.
	 */
	public AsyncServiceInstance(String jobId, String serviceId, String instanceId, StatusUpdate status, String outputType) {
		this.jobId = jobId;
		this.serviceId = serviceId;
		this.instanceId = instanceId;
		this.status = status;
		this.lastCheckedOn = new DateTime();
		this.outputType = outputType;
		this.numberErrorResponses = 0;
	}

	/**
	 * @return The unique Job ID for this Instance. Generate by UUIDGen.
	 */
	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	/**
	 * @return The unique ID of the User Service
	 */
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * @return The unique ID of the execution instance for this User Service. Generated by User Service
	 */
	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	/**
	 * @return The current status of this execution. @see StatusUpdate
	 */
	public StatusUpdate getStatus() {
		return status;
	}

	public void setStatus(StatusUpdate status) {
		this.status = status;
	}

	/**
	 * @return The timestamp that this instance was last checked for Status.
	 */
	@JsonIgnore
	public DateTime getLastCheckedOn() {
		return lastCheckedOn;
	}

	@JsonIgnore
	public void setLastCheckedOn(DateTime lastCheckedOn) {
		this.lastCheckedOn = lastCheckedOn;
	}

	/**
	 * @return The timestamp that this instance was last checked for Status. Epoch.
	 */
	@JsonProperty("lastCheckedOn")
	public Long getCheckedOnEpoch() {
		if (lastCheckedOn != null) {
			// Defaults to ISO8601
			return lastCheckedOn.getMillis();
		} else {
			return null;
		}
	}

	@JsonProperty("lastCheckedOn")
	public void setCheckedOnEpoch(Long lastCheckedOn) {
		this.lastCheckedOn = new DateTime(lastCheckedOn.longValue());
	}

	/**
	 * @return The number of timeouts/errors a Status Check has received. If too many timeouts are encountered, then an
	 *         Error might be flagged and the Async Instance may be set as a Failure.
	 */
	public Integer getNumberErrorResponses() {
		return numberErrorResponses;
	}

	public void setNumberErrorResponses(Integer numberErrorResponses) {
		this.numberErrorResponses = numberErrorResponses;
	}

	public String getOutputType() {
		return outputType;
	}

	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}
}