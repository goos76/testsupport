import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './boekingbestand.reducer';
import { IBoekingbestand } from 'app/shared/model/boekingbestand.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBoekingbestandDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BoekingbestandDetail = (props: IBoekingbestandDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { boekingbestandEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Boekingbestand [<b>{boekingbestandEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="kenmerk">Kenmerk</span>
          </dt>
          <dd>{boekingbestandEntity.kenmerk}</dd>
          <dt>
            <span id="label">Label</span>
          </dt>
          <dd>{boekingbestandEntity.label}</dd>
        </dl>
        <Button tag={Link} to="/boekingbestand" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/boekingbestand/${boekingbestandEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ boekingbestand }: IRootState) => ({
  boekingbestandEntity: boekingbestand.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BoekingbestandDetail);
