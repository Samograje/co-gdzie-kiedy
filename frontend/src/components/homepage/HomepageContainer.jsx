import React, {Component} from 'react';
import HomepageComponent from './HomepageComponent';

class HomepageContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: true,
      error: false,
      affiliationsCount: null,
      computerSetsCount: null,
      hardwareCount: null,
      softwareCount: null,
    };
  }

  componentDidMount() {
    fetch('/api/statistics')
      .then((response) => response.json())
      .then((response) => {
        this.setState({
          loading: false,
          error: false,
          ...response
        })
      })
      .catch((response) => {
        this.setState({
          loading: false,
          error: true,
        });
      })
  }

  render() {
    return (
      <HomepageComponent
        goToAffiliations={() => this.props.history.push('/affiliations')}
        goToComputerSets={() => this.props.history.push('/computer-sets')}
        goToHardware={() => this.props.history.push('/hardware')}
        goToSoftware={() => this.props.history.push('/software')}
        {...this.state}
      />
    );
  }
}

export default HomepageContainer;