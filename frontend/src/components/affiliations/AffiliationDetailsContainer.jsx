import React, {Component} from 'react';
import {Dimensions} from 'react-native';
import AffiliationDetailsComponent from './AffiliationDetailsComponent';
import request from '../../APIClient';

class AffiliationDetailsContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: {
        firstName: '',
        lastName: '',
        location: '',
      },
      error: false,
      errors: {},
      isLoading: false,
      isSubmitting: false,
      dialogOpened: false,
    };
  }

  componentDidMount() {
    this._isMounted = true;
    if (this.props.mode === 'edit') {
      this.loadInitialData();
    }
  }

  componentWillUnmount() {
    this._isMounted = false;
  }

  loadInitialData = () => {
    this.setState({
      isLoading: true,
    });
    request(`/api/affiliations/${this.props.id}`)
      .then((response) => response.json())
      .then((response) => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          data: response,
          isLoading: false,
        });
      })
      .catch(() => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          isLoading: false,
          error: true,
        });
      });
  };

  validateData = (data) => {
    const errors = {};
    if (!data.firstName && !data.lastName && !data.location) {
      const errorMessage = 'Należy podać wartość w co najmniej jednym polu';
      errors.firstName = errorMessage;
      errors.lastName = errorMessage;
      errors.location = errorMessage;
    }
    return errors;
  };

  onChange = (fieldName, value) => {
    const newData = {
      ...this.state.data,
      [fieldName]: value,
    };

    const newErrors = this.validateData(newData);

    this.setState({
      data: newData,
      errors: newErrors,
    });
  };

  onSubmit = () => {
    const errors = this.validateData(this.state.data);
    const noErrors = Object.keys(errors).length === 0;
    if (!noErrors) {
      this.setState({
        errors,
      });
    } else {
      this.sendData();
    }
  };

  sendData = () => {
    const {id, mode} = this.props;

    let url;
    let method;
    if (mode === 'create') {
      url = '/api/affiliations';
      method = 'POST';
    }
    if (mode === 'edit') {
      url = `/api/affiliations/${id}`;
      method = 'PUT';
    }

    this.setState({
      isSubmitting: true,
    });
    request(url, {
      method: method,
      body: JSON.stringify(this.state.data),
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      },
    })
      .then((response) => response.json())
      .then((response) => {
        if (response.success) {
          this.props.goBack();
        } else {
          if (!this._isMounted) {
            return;
          }
          this.setState({
            error: true,
            isSubmitting: false,
          });
        }
      })
      .catch(() => {
        if (!this._isMounted) {
          return;
        }
        this.setState({
          error: true,
          isSubmitting: false,
        });
      });
  };

  onReject = () => this.setState({dialogOpened: true});
  closeDialog = () => this.setState({dialogOpened: false});
  confirmDialog = () => this.props.goBack();
  render() {
    const isWide = Dimensions.get('window').width > 450;
    const {mode} = this.props;
    const {
      data,
      error,
      errors,
      isLoading,
      isSubmitting,
    } = this.state;

    return (
      <AffiliationDetailsComponent
        data={data}
        mode={mode}
        error={error}
        errors={errors}
        isLoading={isLoading}
        isSubmitting={isSubmitting}
        isWide={isWide}
        onSubmit={this.onSubmit}
        onReject={this.onReject}
        onChange={this.onChange}
        dialogOpened={this.state.dialogOpened}
        dialogHandleReject={this.closeDialog}
        dialogHandleConfirm={this.confirmDialog}
      />
    );
  }
}

export default AffiliationDetailsContainer;
