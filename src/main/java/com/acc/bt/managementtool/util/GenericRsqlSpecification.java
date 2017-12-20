package com.acc.bt.managementtool.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;

public class GenericRsqlSpecification<T> implements Specification<T> {
	private String property;
	private ComparisonOperator operator;
	private List<String> arguments;

	public GenericRsqlSpecification(String property, ComparisonOperator operator, List<String> arguments) {
		super();
		this.property = property;
		this.operator = operator;
		this.arguments = arguments;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		List<Object> args = castArguments(root);
		Object argument = args.get(0);
		switch (RsqlSearchOperation.getSimpleOperator(operator)) {

		case EQUAL: {
			
			if (property.contains(".")) {
				argument = argument.toString().replace('*', '%');
				String[] el = property.split("\\.");
				Expression<T> ex = root.join(el[0]).get(el[1]);
				return builder.equal(ex, argument);
			}
			if (argument instanceof String) {
				argument = argument.toString().replace('*', '%');
				return builder.like(root.<String>get(property), argument.toString().replace('*', '%'));
			}
		    if (argument == null) {
				return builder.isNull(root.get(property));
			} else {
				return builder.equal(root.get(property), argument);
			}
		}
		case NOT_EQUAL: {
			if (argument instanceof String) {
				return builder.notLike(root.<String>get(property), argument.toString().replace('*', '%'));
			} else if (argument == null) {
				return builder.isNotNull(root.get(property));
			} else {
				return builder.notEqual(root.get(property), argument);
			}
		}
		case GREATER_THAN: {
			if (argument instanceof Date) {
				return builder.greaterThan(root.<Date>get(property), (Date) argument);
			} else {
				return builder.greaterThan(root.<String>get(property), argument.toString());
			}
		}
		case GREATER_THAN_OR_EQUAL: {
			if (argument instanceof Date) {
				return builder.greaterThanOrEqualTo(root.<Date>get(property), (Date) argument);
			} else {
				return builder.greaterThanOrEqualTo(root.<String>get(property), argument.toString());
			}
		}
		case LESS_THAN: {
			if (argument instanceof Date) {
				return builder.lessThan(root.<Date>get(property), (Date) argument);
			} else {
				return builder.lessThan(root.<String>get(property), argument.toString());
			}
		}
		case LESS_THAN_OR_EQUAL: {
			if (argument instanceof Date) {
				return builder.lessThanOrEqualTo(root.<Date>get(property), (Date) argument);
			} else {
				return builder.lessThanOrEqualTo(root.<String>get(property), argument.toString());
			}
		}
		case IN:
			return root.get(property).in(args);
		case NOT_IN:
			return builder.not(root.get(property).in(args));
		}

		return null;
	}

	private List<Object> castArguments(Root<T> root) {
		List<Object> args = new ArrayList<Object>();
		Class<? extends Object> type;
		if (property.contains(".")) {
			String[] el = property.split("\\.");
			 type = root.join(el[0]).get(el[1]).getJavaType();
		} else {
			type = root.get(property).getJavaType();
		}
		for (String argument : arguments) {
			if (type.equals(Integer.class)) {
				args.add(Integer.parseInt(argument));
			} else if (type.equals(Long.class)) {
				args.add(Long.parseLong(argument));
			} else if (type.equals(Date.class)) {
				args.add(new Date(Long.parseLong(argument)));
			} else {

				args.add(argument);
			}
		}

		return args;
	}
}
