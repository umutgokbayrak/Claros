package org.claros.intouch.webmail.models;

import org.claros.intouch.common.utility.Constants;

/**
 * @author Umut Gokbayrak
 */
public class MsgRuleWrapper extends MsgRule {
	private String destinationName = null;
	private String actionName = null;
	private String conditionName = null;
	private String portionName = null;

	/**
	 * @param tmp
	 */
	public MsgRuleWrapper(MsgRule tmp) {
		setRuleAction(tmp.getRuleAction());
		setRuleCondition(tmp.getRuleCondition());
		setDestination(tmp.getDestination());
		setId(tmp.getId());
		setKeyword(tmp.getKeyword());
		setPortion(tmp.getPortion());
		setUsername(tmp.getUsername());
		
		if (tmp.getRuleAction().equals(Constants.ACTION_DELETE)) {
			setActionName(Constants.ACTION_DELETE_STR);
		} else if (tmp.getRuleAction().equals(Constants.ACTION_MOVE)) {
			setActionName(Constants.ACTION_MOVE_STR);
		}
		
		if (tmp.getRuleCondition().equals(Constants.CONDITION_CONTAINS)) {
			setConditionName(Constants.CONDITION_CONTAINS_STR);
		} else if (tmp.getRuleCondition().equals(Constants.CONDITION_EQUALS)) {
			setConditionName(Constants.CONDITION_EQUALS_STR);
		} else if (tmp.getRuleCondition().equals(Constants.CONDITION_NOT_CONTAINS)) {
			setConditionName(Constants.CONDITION_NOT_CONTAINS_STR);
		}
		
		if (tmp.getPortion().equals(Constants.PORTION_CC)) {
			setPortionName(Constants.PORTION_CC_STR);
		} else if (tmp.getPortion().equals(Constants.PORTION_FROM)) {
			setPortionName(Constants.PORTION_FROM_STR);
		} else if (tmp.getPortion().equals(Constants.PORTION_MESSAGE_BODY)) {
			setPortionName(Constants.PORTION_MESSAGE_BODY_STR);
		} else if (tmp.getPortion().equals(Constants.PORTION_SUBJECT)) {
			setPortionName(Constants.PORTION_SUBJECT_STR);
		} else if (tmp.getPortion().equals(Constants.PORTION_TO)) {
			setPortionName(Constants.PORTION_TO_STR);
		}
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getConditionName() {
		return conditionName;
	}

	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getPortionName() {
		return portionName;
	}

	public void setPortionName(String portionName) {
		this.portionName = portionName;
	}

}
