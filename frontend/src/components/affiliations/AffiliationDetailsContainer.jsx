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
      isWide: Dimensions.get('window').width > 650,
    };
  }

  componentDidMount() {
    if (this.props.mode === 'edit') {
      this.loadInitialData();
    }
  }

  loadInitialData = () => {
    this.setState({
      isLoading: true,
    });
    request(`/api/affiliations/${this.props.id}`)
      .then((response) => response.json())
      .then((response) => {
        this.setState({
          data: response,
          isLoading: false,
        });
      })
      .catch(() => {
        this.setState({
          isLoading: false,
          error: true,
        });
      });
  };

  onChange = (fieldName, value) => {
    const newErrors = {...this.state.errors};
    if (!value) {
      newErrors[fieldName] = 'To pole jest wymagane';
    } else {
      newErrors[fieldName] = null;
    }
    this.setState((prevState) => ({
      data: {
        ...prevState.data,
        [fieldName]: value,
      },
      errors: newErrors,
    }));
  };

  onSubmit = () => {
    const errors = {};
    Object.keys(this.state.data).forEach((key) => {
      const value = this.state.data[key];
      if (!value) {
        errors[key] = 'To pole jest wymagane';
      }
    });
    const noErrors = Object.keys(errors).length === 0;
    if (!noErrors) {
      this.setState({
        errors,
      });
    } else {
      this.setState({
        isSubmitting: true,
      });
      console.log(this.state.data);
    }
  };

  onReject = () => this.props.goBack();

  onLayout = () => {
    this.setState({
      isWide: Dimensions.get('window').width > 650,
    });
  };

  render() {
    const {mode} = this.props;
    const {
      error,
      errors,
      isLoading,
      isSubmitting,
      isWide,
    } = this.state;

    return (
      <AffiliationDetailsComponent
        mode={mode}
        error={error}
        errors={errors}
        isLoading={isLoading}
        isSubmitting={isSubmitting}
        isWide={isWide}
        onSubmit={this.onSubmit}
        onReject={this.onReject}
        onChange={this.onChange}
        onLayout={this.onLayout}
      />
    );
  }
}

export default AffiliationDetailsContainer;
