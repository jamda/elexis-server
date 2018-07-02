package es.fhir.rest.core.resources;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hl7.fhir.dstu3.model.Appointment;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.param.DateRangeParam;
import es.fhir.rest.core.IFhirResourceProvider;
import es.fhir.rest.core.IFhirTransformer;
import es.fhir.rest.core.IFhirTransformerRegistry;
import es.fhir.rest.core.resources.util.QueryUtil;
import info.elexis.server.core.common.converter.DateConverter;
import info.elexis.server.core.connector.elexis.jpa.model.annotated.Termin;
import info.elexis.server.core.connector.elexis.jpa.model.annotated.Termin_;
import info.elexis.server.core.connector.elexis.services.JPAQuery;
import info.elexis.server.core.connector.elexis.services.JPAQuery.QUERY;
import info.elexis.server.core.connector.elexis.services.TerminService;

@Component
public class AppointmentResourceProvider implements IFhirResourceProvider {

	@Override
	public Class<? extends IBaseResource> getResourceType() {
		return Appointment.class;
	}

	private IFhirTransformerRegistry transformerRegistry;

	@Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, unbind = "-")
	protected void bindIFhirTransformerRegistry(IFhirTransformerRegistry transformerRegistry) {
		this.transformerRegistry = transformerRegistry;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IFhirTransformer<Appointment, Termin> getTransformer() {
		return (IFhirTransformer<Appointment, Termin>) transformerRegistry.getTransformerFor(Appointment.class,
				Termin.class);
	}

	@Read
	public Appointment getResourceById(@IdParam IdType theId) {
		String idPart = theId.getIdPart();
		if (idPart != null) {
			Optional<Termin> appointment = TerminService.load(idPart);
			if (appointment.isPresent()) {
				Optional<Appointment> fhirAppointment = getTransformer().getFhirObject(appointment.get());
				return fhirAppointment.get();
			}
		}
		return null;
	}

	@Search
	public List<Appointment> findAppointmentsByDate(
			@RequiredParam(name = Appointment.SP_DATE) DateRangeParam dateParam) {
		JPAQuery<Termin> query = new JPAQuery<>(Termin.class);

		DateConverter dateConverter = new DateConverter();

		// TODO support minutes
		if (dateParam.getLowerBound() != null) {
			LocalDate dayParam = dateConverter.convertToLocalDate(dateParam.getLowerBound().getValue());
			QUERY compare = QueryUtil.prefixParamToToQueryParam(dateParam.getLowerBound().getPrefix());
			query.add(Termin_.tag, compare, dayParam);
		}
		if (dateParam.getUpperBound() != null) {
			LocalDate dayParam2 = dateConverter.convertToLocalDate(dateParam.getUpperBound().getValue());
			QUERY compare2 = QueryUtil.prefixParamToToQueryParam(dateParam.getUpperBound().getPrefix());
			query.add(Termin_.tag, compare2, dayParam2);
		}

		List<Termin> appointments = query.execute();
		if (!appointments.isEmpty()) {
			return appointments.parallelStream().map(a -> getTransformer().getFhirObject(a).get())
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

}